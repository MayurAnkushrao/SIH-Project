package com.sih.SIHbackend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sih.SIHbackend.dto.request.MLPredictRequest;
import com.sih.SIHbackend.dto.response.MLPredictResponse;
import com.sih.SIHbackend.dto.response.SentimentResponse;
import com.sih.SIHbackend.dto.response.SentimentStatsResponse;
import com.sih.SIHbackend.entity.Comment;
import com.sih.SIHbackend.entity.Post;
import com.sih.SIHbackend.entity.SentimentAnalysis;
import com.sih.SIHbackend.enums.SentimentType;
import com.sih.SIHbackend.repository.CommentRepository;
import com.sih.SIHbackend.repository.PostRepository;
import com.sih.SIHbackend.repository.SentimentAnalysisRepository;
import com.sih.SIHbackend.service.SentimentService;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SentimentServiceImpl implements SentimentService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private SentimentAnalysisRepository sentimentAnalysisRepository;
    
    @Value("${ml.model.url:http://127.0.0.1:5000}")
    private String ML_BASE_URL;
    
    @Override
    public SentimentResponse analyzeSentiment(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        List<Comment> comments = commentRepository.findByPostId(postId);
        
        if (comments.isEmpty()) {
            return createEmptyResponse(post, "No comments available for analysis");
        }
        
        try {
            List<MLPredictResponse> mlResults = new ArrayList<>();
            Map<String, Integer> sentimentCounts = new HashMap<>();
            sentimentCounts.put("positive", 0);
            sentimentCounts.put("negative", 0);
            sentimentCounts.put("neutral", 0);
            
            for (Comment comment : comments) {
                MLPredictRequest request = new MLPredictRequest(comment.getContent());
                try {
                    ResponseEntity<MLPredictResponse> response = restTemplate.postForEntity(
                        ML_BASE_URL + "/predict", request, MLPredictResponse.class);
                    
                    if (response.getBody() != null) {
                        MLPredictResponse result = response.getBody();
                        mlResults.add(result);
                        updateCommentSentiment(comment, result);
                        String predictedLabel = result.getPredictedLabel().toLowerCase();
                        sentimentCounts.merge(predictedLabel, 1, Integer::sum);
                    }
                } catch (Exception e) {
                    comment.setSentiment(SentimentType.NEUTRAL);
                    comment.setConfidenceScore(BigDecimal.ZERO);
                    commentRepository.save(comment);
                    sentimentCounts.merge("neutral", 1, Integer::sum);
                }
            }
            
            // Aggregate comments for summarization
            StringBuilder allCommentsText = new StringBuilder();
            for (Comment comment : comments) {
                allCommentsText.append(comment.getContent()).append(" ");
            }
            String summaryText = null;
            try {
                MLPredictRequest summarizeRequest = new MLPredictRequest(allCommentsText.toString().trim());
                ResponseEntity<MLPredictResponse> summaryResponse = restTemplate.postForEntity(
                    ML_BASE_URL + "/summarize", summarizeRequest, MLPredictResponse.class);
                if (summaryResponse.getBody() != null) {
                    summaryText = summaryResponse.getBody().getSummary();
                }
            } catch (Exception e) {
                summaryText = null; // fallback to default summary below
            }
            
            SentimentAnalysis analysis = createSentimentAnalysis(post, comments, sentimentCounts, mlResults);
            if (summaryText != null) {
                analysis.setSummary(summaryText);
                sentimentAnalysisRepository.save(analysis);
            }
            
            return convertToResponse(analysis, "Analysis completed successfully");
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to analyze sentiment: " + e.getMessage(), e);
        }
    }
    
    private void updateCommentSentiment(Comment comment, MLPredictResponse mlResult) {
        SentimentType sentimentType;
        switch (mlResult.getPredictedLabel().toLowerCase()) {
            case "positive": sentimentType = SentimentType.POSITIVE; break;
            case "negative": sentimentType = SentimentType.NEGATIVE; break;
            default: sentimentType = SentimentType.NEUTRAL; break;
        }
        comment.setSentiment(sentimentType);
        comment.setConfidenceScore(BigDecimal.valueOf(mlResult.getConfidence()));
        commentRepository.save(comment);
    }
    
    private SentimentAnalysis createSentimentAnalysis(Post post, List<Comment> comments, 
                                                     Map<String, Integer> sentimentCounts, 
                                                     List<MLPredictResponse> mlResults) {
        SentimentAnalysis analysis = sentimentAnalysisRepository.findByPost(post)
            .orElse(new SentimentAnalysis());
        analysis.setPost(post);
        analysis.setTotalComments(comments.size());
        analysis.setPositiveCount(sentimentCounts.get("positive"));
        analysis.setNegativeCount(sentimentCounts.get("negative"));
        analysis.setNeutralCount(sentimentCounts.get("neutral"));
        analysis.setAnalyzedAt(LocalDateTime.now());
        analysis.calculatePercentages();
        if (analysis.getSummary() == null) {
            // Only generate default summary if ML summary not set
            analysis.setSummary(generateSummary(sentimentCounts, comments.size()));
        }
        analysis.setKeywords(generateKeywords(mlResults));
        return sentimentAnalysisRepository.save(analysis);
    }
    
    private String generateSummary(Map<String, Integer> sentimentCounts, int totalComments) {
        int positive = sentimentCounts.get("positive");
        int negative = sentimentCounts.get("negative");
        int neutral = sentimentCounts.get("neutral");
        String dominantSentiment;
        if (positive >= negative && positive >= neutral) dominantSentiment = "positive";
        else if (negative >= positive && negative >= neutral) dominantSentiment = "negative";
        else dominantSentiment = "neutral";
        return String.format("Analysis of %d comments shows predominantly %s sentiment. Positive: %d, Negative: %d, Neutral: %d", 
                             totalComments, dominantSentiment, positive, negative, neutral);
    }
    
    private String generateKeywords(List<MLPredictResponse> mlResults) {
        Set<String> keywords = new HashSet<>();
        for (MLPredictResponse result : mlResults) {
            if (result.getConfidenceCategory() != null) {
                keywords.add(result.getConfidenceCategory().toLowerCase());
            }
            keywords.add(result.getPredictedLabel().toLowerCase());
        }
        return String.join(", ", keywords);
    }
    
    // ... keep other methods unchanged, omitted here for brevity ...


    

    
    @Override
    public boolean checkMLModelHealth() {
        try {
            MLPredictRequest testRequest = new MLPredictRequest("test");
            ResponseEntity<MLPredictResponse> response = restTemplate.postForEntity(
                ML_BASE_URL + "/predict", testRequest, MLPredictResponse.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public SentimentResponse getSentimentResults(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        SentimentAnalysis analysis = sentimentAnalysisRepository.findByPost(post)
            .orElseThrow(() -> new RuntimeException("No sentiment analysis found for post: " + postId));
        
        return convertToResponse(analysis, "Results retrieved successfully");
    }
    
    @Override
    public SentimentStatsResponse getSentimentStatistics(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        SentimentAnalysis analysis = sentimentAnalysisRepository.findByPost(post).orElse(null);
        
        if (analysis == null) {
            return new SentimentStatsResponse(postId, post.getTitle(), 0, null, null, null,
                BigDecimal.ZERO, "No Analysis", null, false);
        }
        
        // Calculate statistics
        Double avgPositiveConf = commentRepository.getAverageConfidenceByPostAndSentiment(postId, SentimentType.POSITIVE);
        Double avgNegativeConf = commentRepository.getAverageConfidenceByPostAndSentiment(postId, SentimentType.NEGATIVE);
        Double avgNeutralConf = commentRepository.getAverageConfidenceByPostAndSentiment(postId, SentimentType.NEUTRAL);
        
        // Find dominant sentiment
        String dominantSentiment = "NEUTRAL";
        BigDecimal dominantPercentage = analysis.getNeutralPercentage();
        
        if (analysis.getPositivePercentage().compareTo(dominantPercentage) > 0) {
            dominantSentiment = "POSITIVE";
            dominantPercentage = analysis.getPositivePercentage();
        }
        if (analysis.getNegativePercentage().compareTo(dominantPercentage) > 0) {
            dominantSentiment = "NEGATIVE";
            dominantPercentage = analysis.getNegativePercentage();
        }
        
        return new SentimentStatsResponse(postId, post.getTitle(), analysis.getTotalComments(),
            avgPositiveConf, avgNegativeConf, avgNeutralConf, dominantPercentage,
            dominantSentiment, analysis.getAnalyzedAt(), true);
    }
    
    @Override
    public SentimentResponse reanalyzeSentiment(Long postId) {
        sentimentAnalysisRepository.findByPostId(postId).ifPresent(analysis -> {
            sentimentAnalysisRepository.deleteById(analysis.getId());
        });
        return analyzeSentiment(postId);
    }
    
    @Override
    public List<SentimentResponse> getAllSentimentResults(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sentimentAnalysisRepository.findAll(pageable).getContent().stream()
            .map(analysis -> convertToResponse(analysis, "Retrieved"))
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteSentimentResults(Long postId) {
        SentimentAnalysis analysis = sentimentAnalysisRepository.findByPostId(postId)
            .orElseThrow(() -> new RuntimeException("No sentiment analysis found for post: " + postId));
        
        sentimentAnalysisRepository.deleteById(analysis.getId());
    }
    
    private SentimentResponse createEmptyResponse(Post post, String message) {
        return new SentimentResponse(null, post.getId(), post.getTitle(), 0, 0, 0, 0,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, SentimentType.NEUTRAL,
            "No comments to analyze", Arrays.asList(), LocalDateTime.now(),
            "EMPTY", message);
    }
    
    // ✅ Fixed: Corrected parameter order and added missing totalComments
    private SentimentResponse convertToResponse(SentimentAnalysis analysis, String message) {
        List<String> keywords = analysis.getKeywords() != null ? 
            Arrays.asList(analysis.getKeywords().split(", ")) : Arrays.asList();
        
        // Determine overall sentiment
        SentimentType overallSentiment = SentimentType.NEUTRAL;
        BigDecimal maxPercentage = analysis.getNeutralPercentage();
        
        if (analysis.getPositivePercentage().compareTo(maxPercentage) > 0) {
            overallSentiment = SentimentType.POSITIVE;
        } else if (analysis.getNegativePercentage().compareTo(maxPercentage) > 0) {
            overallSentiment = SentimentType.NEGATIVE;
        }
        
        return new SentimentResponse(
            analysis.getId(),                    // analysisId
            analysis.getPostId(),                // postId  
            analysis.getPost().getTitle(),       // postTitle
            analysis.getTotalComments(),         // ✅ Fixed: Added totalComments
            analysis.getPositiveCount(),         // positiveCount
            analysis.getNegativeCount(),         // negativeCount
            analysis.getNeutralCount(),          // neutralCount
            analysis.getPositivePercentage(),    // positivePercentage
            analysis.getNegativePercentage(),    // negativePercentage
            analysis.getNeutralPercentage(),     // neutralPercentage
            overallSentiment,                    // overallSentiment
            analysis.getSummary(),               // summary
            keywords,                            // keywords
            analysis.getAnalyzedAt(),            // analyzedAt
            "SUCCESS",                           // status
            message                              // message
        );
    }
}

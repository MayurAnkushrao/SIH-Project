package com.sih.SIHbackend.dto.response;

import com.sih.SIHbackend.enums.SentimentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SentimentResponse {
    
    private Long analysisId;
    private Long postId;
    private String postTitle;
    private Integer totalComments;        // ✅ Make sure this field exists
    private Integer positiveCount;
    private Integer negativeCount;
    private Integer neutralCount;
    private BigDecimal positivePercentage;
    private BigDecimal negativePercentage;
    private BigDecimal neutralPercentage;
    private SentimentType overallSentiment;
    private String summary;
    private List<String> keywords;
    private LocalDateTime analyzedAt;
    private String status;
    private String message;
    
    // Default constructor
    public SentimentResponse() {}
    
    // Constructor for error responses
    public SentimentResponse(Long postId, String status, String message) {
        this.postId = postId;
        this.status = status;
        this.message = message;
    }
    
    // ✅ Full constructor with correct parameter order
    public SentimentResponse(Long analysisId, Long postId, String postTitle,
                           Integer totalComments, Integer positiveCount, Integer negativeCount,
                           Integer neutralCount, BigDecimal positivePercentage, BigDecimal negativePercentage,
                           BigDecimal neutralPercentage, SentimentType overallSentiment,
                           String summary, List<String> keywords, LocalDateTime analyzedAt,
                           String status, String message) {
        this.analysisId = analysisId;
        this.postId = postId;
        this.postTitle = postTitle;
        this.totalComments = totalComments;    // ✅ Fixed: Added totalComments
        this.positiveCount = positiveCount;
        this.negativeCount = negativeCount;
        this.neutralCount = neutralCount;
        this.positivePercentage = positivePercentage;
        this.negativePercentage = negativePercentage;
        this.neutralPercentage = neutralPercentage;
        this.overallSentiment = overallSentiment;
        this.summary = summary;
        this.keywords = keywords;
        this.analyzedAt = analyzedAt;
        this.status = status;
        this.message = message;
    }
    
    // ✅ Make sure all getters and setters are present
    public Long getAnalysisId() { return analysisId; }
    public void setAnalysisId(Long analysisId) { this.analysisId = analysisId; }
    
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    
    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
    
    public Integer getTotalComments() { return totalComments; }
    public void setTotalComments(Integer totalComments) { this.totalComments = totalComments; }
    
    // ... rest of getters and setters
    public Integer getPositiveCount() { return positiveCount; }
    public void setPositiveCount(Integer positiveCount) { this.positiveCount = positiveCount; }
    
    public Integer getNegativeCount() { return negativeCount; }
    public void setNegativeCount(Integer negativeCount) { this.negativeCount = negativeCount; }
    
    public Integer getNeutralCount() { return neutralCount; }
    public void setNeutralCount(Integer neutralCount) { this.neutralCount = neutralCount; }
    
    public BigDecimal getPositivePercentage() { return positivePercentage; }
    public void setPositivePercentage(BigDecimal positivePercentage) { this.positivePercentage = positivePercentage; }
    
    public BigDecimal getNegativePercentage() { return negativePercentage; }
    public void setNegativePercentage(BigDecimal negativePercentage) { this.negativePercentage = negativePercentage; }
    
    public BigDecimal getNeutralPercentage() { return neutralPercentage; }
    public void setNeutralPercentage(BigDecimal neutralPercentage) { this.neutralPercentage = neutralPercentage; }
    
    public SentimentType getOverallSentiment() { return overallSentiment; }
    public void setOverallSentiment(SentimentType overallSentiment) { this.overallSentiment = overallSentiment; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
    
    public LocalDateTime getAnalyzedAt() { return analyzedAt; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

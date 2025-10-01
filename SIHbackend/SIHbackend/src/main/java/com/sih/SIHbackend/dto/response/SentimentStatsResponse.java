package com.sih.SIHbackend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SentimentStatsResponse {
    
    private Long postId;
    private String postTitle;
    private Integer totalComments;
    private Double averagePositiveConfidence;
    private Double averageNegativeConfidence;
    private Double averageNeutralConfidence;
    private BigDecimal dominantSentimentPercentage;
    private String dominantSentiment;
    private LocalDateTime lastAnalyzedAt;
    private Boolean analysisAvailable;
    
    // Default constructor
    public SentimentStatsResponse() {}
    
    // Full constructor
    public SentimentStatsResponse(Long postId, String postTitle, Integer totalComments,
                                 Double averagePositiveConfidence, Double averageNegativeConfidence,
                                 Double averageNeutralConfidence, BigDecimal dominantSentimentPercentage,
                                 String dominantSentiment, LocalDateTime lastAnalyzedAt,
                                 Boolean analysisAvailable) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.totalComments = totalComments;
        this.averagePositiveConfidence = averagePositiveConfidence;
        this.averageNegativeConfidence = averageNegativeConfidence;
        this.averageNeutralConfidence = averageNeutralConfidence;
        this.dominantSentimentPercentage = dominantSentimentPercentage;
        this.dominantSentiment = dominantSentiment;
        this.lastAnalyzedAt = lastAnalyzedAt;
        this.analysisAvailable = analysisAvailable;
    }
    
    // Getters and Setters
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    
    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
    
    public Integer getTotalComments() { return totalComments; }
    public void setTotalComments(Integer totalComments) { this.totalComments = totalComments; }
    
    public Double getAveragePositiveConfidence() { return averagePositiveConfidence; }
    public void setAveragePositiveConfidence(Double averagePositiveConfidence) { this.averagePositiveConfidence = averagePositiveConfidence; }
    
    public Double getAverageNegativeConfidence() { return averageNegativeConfidence; }
    public void setAverageNegativeConfidence(Double averageNegativeConfidence) { this.averageNegativeConfidence = averageNegativeConfidence; }
    
    public Double getAverageNeutralConfidence() { return averageNeutralConfidence; }
    public void setAverageNeutralConfidence(Double averageNeutralConfidence) { this.averageNeutralConfidence = averageNeutralConfidence; }
    
    public BigDecimal getDominantSentimentPercentage() { return dominantSentimentPercentage; }
    public void setDominantSentimentPercentage(BigDecimal dominantSentimentPercentage) { this.dominantSentimentPercentage = dominantSentimentPercentage; }
    
    public String getDominantSentiment() { return dominantSentiment; }
    public void setDominantSentiment(String dominantSentiment) { this.dominantSentiment = dominantSentiment; }
    
    public LocalDateTime getLastAnalyzedAt() { return lastAnalyzedAt; }
    public void setLastAnalyzedAt(LocalDateTime lastAnalyzedAt) { this.lastAnalyzedAt = lastAnalyzedAt; }
    
    public Boolean getAnalysisAvailable() { return analysisAvailable; }
    public void setAnalysisAvailable(Boolean analysisAvailable) { this.analysisAvailable = analysisAvailable; }
}

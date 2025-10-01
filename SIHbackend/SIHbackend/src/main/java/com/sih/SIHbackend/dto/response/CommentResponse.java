package com.sih.SIHbackend.dto.response;

import com.sih.SIHbackend.enums.SentimentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CommentResponse {
    
    private Long id;
    private Long postId;
    private String postTitle;
    private Long userId;
    private String userName;
    private String userEmail;
    private String content;
    private SentimentType sentiment;
    private BigDecimal confidenceScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Default constructor
    public CommentResponse() {}
    
    // Constructor with all fields
    public CommentResponse(Long id, Long postId, String postTitle, Long userId, 
                          String userName, String userEmail, String content, 
                          SentimentType sentiment, BigDecimal confidenceScore,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.postTitle = postTitle;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.content = content;
        this.sentiment = sentiment;
        this.confidenceScore = confidenceScore;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    
    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public SentimentType getSentiment() { return sentiment; }
    public void setSentiment(SentimentType sentiment) { this.sentiment = sentiment; }
    
    public BigDecimal getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(BigDecimal confidenceScore) { this.confidenceScore = confidenceScore; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

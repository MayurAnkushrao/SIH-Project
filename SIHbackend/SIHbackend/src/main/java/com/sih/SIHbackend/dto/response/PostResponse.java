package com.sih.SIHbackend.dto.response;

import com.sih.SIHbackend.enums.PostStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostResponse {
    
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private String author;
    private PostStatus status;
    private Long createdById;
    private String createdByName;
    private String createdByEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer commentCount;
    private Boolean hasAnalysis;
    private Boolean isExpired;
    
    // Default constructor
    public PostResponse() {}
    
    // Constructor with all fields
    public PostResponse(Long id, String title, String description, LocalDate deadline,
                       String author, PostStatus status, Long createdById, String createdByName,
                       String createdByEmail, LocalDateTime createdAt, LocalDateTime updatedAt,
                       Integer commentCount, Boolean hasAnalysis) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.author = author;
        this.status = status;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.createdByEmail = createdByEmail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentCount = commentCount;
        this.hasAnalysis = hasAnalysis;
        this.isExpired = deadline != null && deadline.isBefore(LocalDate.now());
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { 
        this.deadline = deadline; 
        this.isExpired = deadline != null && deadline.isBefore(LocalDate.now());
    }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public PostStatus getStatus() { return status; }
    public void setStatus(PostStatus status) { this.status = status; }
    
    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }
    
    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }
    
    public String getCreatedByEmail() { return createdByEmail; }
    public void setCreatedByEmail(String createdByEmail) { this.createdByEmail = createdByEmail; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Integer getCommentCount() { return commentCount; }
    public void setCommentCount(Integer commentCount) { this.commentCount = commentCount; }
    
    public Boolean getHasAnalysis() { return hasAnalysis; }
    public void setHasAnalysis(Boolean hasAnalysis) { this.hasAnalysis = hasAnalysis; }
    
    public Boolean getIsExpired() { return isExpired; }
    public void setIsExpired(Boolean isExpired) { this.isExpired = isExpired; }
}

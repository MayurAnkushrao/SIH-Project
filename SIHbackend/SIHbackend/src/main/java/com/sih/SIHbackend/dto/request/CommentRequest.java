package com.sih.SIHbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentRequest {
    
    @NotNull(message = "Post ID is required")
    private Long postId;
    
    @NotBlank(message = "Comment content is required")
    @Size(min = 1, max = 5000, message = "Comment content must be between 1 and 5000 characters")
    private String content;
    
    // Default constructor
    public CommentRequest() {}
    
    // Constructor with parameters
    public CommentRequest(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
    
    // Getters and Setters
    public Long getPostId() {
        return postId;
    }
    
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content != null ? content.trim() : null;
    }
    
    @Override
    public String toString() {
        return "CommentRequest{" +
                "postId=" + postId +
                ", content='" + content + '\'' +
                '}';
    }
}

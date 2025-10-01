package com.sih.SIHbackend.dto.request;

import com.sih.SIHbackend.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;

public class PostRequest {
    
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 500, message = "Title must be between 5 and 500 characters")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 10000, message = "Description must be between 10 and 10000 characters")
    private String description;
    
    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    private LocalDate deadline;
    
    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 200, message = "Author name must be between 2 and 200 characters")
    private String author;
    
    private PostStatus status = PostStatus.ACTIVE;
    
    // Default constructor
    public PostRequest() {}
    
    // Constructor with parameters
    public PostRequest(String title, String description, LocalDate deadline, String author) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.author = author;
    }
    
    // Full constructor
    public PostRequest(String title, String description, LocalDate deadline, String author, PostStatus status) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.author = author;
        this.status = status;
    }
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title != null ? title.trim() : null;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }
    
    public LocalDate getDeadline() {
        return deadline;
    }
    
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author != null ? author.trim() : null;
    }
    
    public PostStatus getStatus() {
        return status;
    }
    
    public void setStatus(PostStatus status) {
        this.status = status != null ? status : PostStatus.ACTIVE;
    }
    
    @Override
    public String toString() {
        return "PostRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", author='" + author + '\'' +
                ", status=" + status +
                '}';
    }
}

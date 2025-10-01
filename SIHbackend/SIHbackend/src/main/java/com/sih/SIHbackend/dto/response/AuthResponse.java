package com.sih.SIHbackend.dto.response;

public class AuthResponse {
    
    private String accessToken;
    private String tokenType;
    private Long userId;
    private String email;
    private String name;  // Single name field
    private String organization;  // Added organization field
    private String role;
    private String message;
    
    // Constructors
    public AuthResponse() {}
    
    public AuthResponse(String accessToken, String tokenType, Long userId, 
                       String email, String name, String role, String message) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.message = message;
    }
    
    // Full constructor with organization
    public AuthResponse(String accessToken, String tokenType, Long userId, 
                       String email, String name, String organization, String role, String message) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.organization = organization;
        this.role = role;
        this.message = message;
    }
    
    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getOrganization() {
        return organization;
    }
    
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}

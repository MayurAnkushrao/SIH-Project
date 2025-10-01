package com.sih.SIHbackend.dto.request;

import jakarta.validation.constraints.NotBlank;

public class MLPredictRequest {
    
    @NotBlank(message = "Text is required for sentiment analysis")
    private String text;
    
    // Default constructor
    public MLPredictRequest() {}
    
    // Constructor
    public MLPredictRequest(String text) {
        this.text = text;
    }
    
    // Getters and Setters
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return "MLPredictRequest{" +
                "text='" + text + '\'' +
                '}';
    }
}

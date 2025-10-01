package com.sih.SIHbackend.dto.response;

import java.util.List;

public class SentimentMLResponse {
    private Integer positiveCount;
    private Integer negativeCount;
    private Integer neutralCount;
    private String summary;
    private List<String> keywords;
    private Double accuracy;
    
    // Constructors
    public SentimentMLResponse() {}
    
    // Getters and Setters
    public Integer getPositiveCount() {
        return positiveCount;
    }
    
    public void setPositiveCount(Integer positiveCount) {
        this.positiveCount = positiveCount;
    }
    
    public Integer getNegativeCount() {
        return negativeCount;
    }
    
    public void setNegativeCount(Integer negativeCount) {
        this.negativeCount = negativeCount;
    }
    
    public Integer getNeutralCount() {
        return neutralCount;
    }
    
    public void setNeutralCount(Integer neutralCount) {
        this.neutralCount = neutralCount;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public List<String> getKeywords() {
        return keywords;
    }
    
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    
    public Double getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }
}

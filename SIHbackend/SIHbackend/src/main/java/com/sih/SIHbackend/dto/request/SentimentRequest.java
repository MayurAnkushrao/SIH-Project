package com.sih.SIHbackend.dto.request;

import java.util.List;

public class SentimentRequest {
    private List<String> texts;
    
    public SentimentRequest() {}
    
    public SentimentRequest(List<String> texts) {
        this.texts = texts;
    }
    
    public List<String> getTexts() {
        return texts;
    }
    
    public void setTexts(List<String> texts) {
        this.texts = texts;
    }
}

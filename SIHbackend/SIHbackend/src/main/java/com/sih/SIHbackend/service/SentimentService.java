package com.sih.SIHbackend.service;

import com.sih.SIHbackend.dto.response.SentimentResponse;
import com.sih.SIHbackend.dto.response.SentimentStatsResponse;

import java.util.List;

public interface SentimentService {
    SentimentResponse analyzeSentiment(Long postId);
    SentimentResponse getSentimentResults(Long postId);
    SentimentStatsResponse getSentimentStatistics(Long postId);
    SentimentResponse reanalyzeSentiment(Long postId);
    List<SentimentResponse> getAllSentimentResults(int page, int size);
    void deleteSentimentResults(Long postId);
    boolean checkMLModelHealth();
}

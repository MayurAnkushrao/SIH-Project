package com.sih.SIHbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sih.SIHbackend.dto.response.SentimentResponse;
import com.sih.SIHbackend.dto.response.SentimentStatsResponse;
import com.sih.SIHbackend.service.SentimentService;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/sentiment")
@CrossOrigin(origins = "*")
@Validated
public class SentimentController {

    @Autowired
    private SentimentService sentimentService;

    @PostMapping("/analyze/{postId}")
    public ResponseEntity analyzeSentiment(@PathVariable @NotNull Long postId) {
        try {
            SentimentResponse response = sentimentService.analyzeSentiment(postId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new SentimentResponse(null, null, "Analysis failed: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SentimentResponse(null, null, "Internal server error during analysis"));
        }
    }

    @GetMapping("/results/{postId}")
    public ResponseEntity getSentimentResults(@PathVariable @NotNull Long postId) {
        try {
            SentimentResponse response = sentimentService.getSentimentResults(postId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stats/{postId}")
    public ResponseEntity getSentimentStats(@PathVariable @NotNull Long postId) {
        try {
            SentimentStatsResponse stats = sentimentService.getSentimentStatistics(postId);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/bulk-results")
    public ResponseEntity<List<SentimentResponse>> getAllSentimentResults(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<SentimentResponse> results = sentimentService.getAllSentimentResults(page, size);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reanalyze/{postId}")
    public ResponseEntity reanalyzeSentiment(@PathVariable @NotNull Long postId) {
        try {
            // Force re-analysis even if analysis already exists
            SentimentResponse response = sentimentService.reanalyzeSentiment(postId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new SentimentResponse(null, null, "Re-analysis failed: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SentimentResponse(null, null, "Internal server error during re-analysis"));
        }
    }

    @DeleteMapping("/results/{postId}")
    public ResponseEntity<DeleteResponse> deleteSentimentResults(@PathVariable @NotNull Long postId) {
        try {
            sentimentService.deleteSentimentResults(postId);
            return ResponseEntity.ok().body(new DeleteResponse("Sentiment analysis results deleted successfully", true));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> checkMLModelHealth() {
        try {
            boolean isHealthy = sentimentService.checkMLModelHealth();
            HealthResponse response = new HealthResponse(isHealthy,
                isHealthy ? "ML model is healthy" : "ML model is not responding");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new HealthResponse(false, "ML model health check failed: " + e.getMessage()));
        }
    }

    // Inner classes for responses
    public static class DeleteResponse {
        private String message;
        private boolean success;

        public DeleteResponse(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        // Getters and setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }

    public static class HealthResponse {
        private boolean healthy;
        private String message;

        public HealthResponse(boolean healthy, String message) {
            this.healthy = healthy;
            this.message = message;
        }

        // Getters and setters
        public boolean isHealthy() { return healthy; }
        public void setHealthy(boolean healthy) { this.healthy = healthy; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}

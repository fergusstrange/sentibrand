package com.sentibrand;

import org.springframework.stereotype.Component;

@Component
public class SentimentResponseFactory {
    public SentimentResponse create(SentimentRequest sentimentRequest, double sentiment) {
        return new SentimentResponse(sentimentRequest.getText(), sentiment);
    }
}

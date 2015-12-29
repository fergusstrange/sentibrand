package com.sentibrand;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SentimentResponse {

    private final String text;
    private final Double sentiment;

    public SentimentResponse(@JsonProperty("text") String text, @JsonProperty("sentiment") Double sentiment) {
        this.text = text;
        this.sentiment = sentiment;
    }

    public String getText() {
        return text;
    }

    public Double getSentiment() {
        return sentiment;
    }
}

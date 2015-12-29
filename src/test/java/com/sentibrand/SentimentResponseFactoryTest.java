package com.sentibrand;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

public class SentimentResponseFactoryTest {

    private final SentimentResponseFactory sentimentResponseFactory = new SentimentResponseFactory();

    @Test
    public void shouldReturnSentimentResponse() throws Exception {
        SentimentResponse sentimentResponse = sentimentResponseFactory.create(new SentimentRequest("Hey Hey Hey"), 3.213213D);

        assertThat(sentimentResponse, samePropertyValuesAs(new SentimentResponse("Hey Hey Hey", 3.213213D)));
    }
}
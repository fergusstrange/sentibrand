package com.sentibrand;

import org.junit.Test;

import java.util.OptionalDouble;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SentimentProcessingServiceTest {

    private final SentimentProcessingService sentimentProcessingService = new SentimentProcessingService();

    @Test
    public void shouldReturnSentimentValue() throws Exception {
        OptionalDouble sentiment = sentimentProcessingService
                .forText("Protest gathering of Parsian Refinery workers in the southern Province of Fars.");

        assertTrue(sentiment.isPresent());
    }

    @Test
      public void shouldReturnLargelyNegativeSentimentValue() throws Exception {
        OptionalDouble sentiment = sentimentProcessingService
                .forText("And no mortgage. RT @hettjones: James Blunt just has an annoying face and a highly irritating voice");

        assertThat(sentiment.getAsDouble(), closeTo(0.6D, 0.1D));
    }

    @Test
    public void shouldReturnLargelyPositiveSentimentValue() throws Exception {
        OptionalDouble sentiment = sentimentProcessingService.forText("Cake is really great. I love the cake.");

        assertThat(sentiment.getAsDouble(), closeTo(1.3D, 0.1D));
    }

    @Test
    public void shouldReturnEmptySentimentValueWhenEmptyString() throws Exception {
        OptionalDouble sentiment = sentimentProcessingService.forText("");

        assertFalse(sentiment.isPresent());
    }

    @Test
    public void shouldReturnEmptySentimentValueWhenBlankString() throws Exception {
        OptionalDouble sentiment = sentimentProcessingService.forText(" ");

        assertFalse(sentiment.isPresent());
    }
}
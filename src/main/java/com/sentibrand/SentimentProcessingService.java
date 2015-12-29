package com.sentibrand;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Component;

import java.util.OptionalDouble;

import static edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class SentimentProcessingService {

    private final StanfordCoreNLP pipeline = new StanfordCoreNLP();

    public OptionalDouble forText(String text) {
        return isNotBlank(text) ?
                averageSentiment(text) :
                OptionalDouble.empty();
    }

    private OptionalDouble averageSentiment(String text) {
        return pipeline.process(text)
                .get(SentencesAnnotation.class)
                .stream()
                .flatMapToDouble(coreMap -> coreMap.get(SentimentAnnotatedTree.class)
                        .stream()
                        .mapToInt(RNNCoreAnnotations::getPredictedClass)
                        .asDoubleStream())
                .average();
    }
}

package com.sentibrand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.OptionalDouble;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.unprocessableEntity;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/sentiment")
public class SentimentProcessingController {

    private static final Logger logger = LoggerFactory.getLogger(SentimentProcessingController.class);

    private final SentimentProcessingService sentimentProcessingService;
    private final SentimentResponseFactory sentimentResponseFactory;

    @Autowired
    public SentimentProcessingController(final SentimentProcessingService sentimentProcessingService,
                                         final SentimentResponseFactory sentimentResponseFactory) {
        this.sentimentProcessingService = sentimentProcessingService;
        this.sentimentResponseFactory = sentimentResponseFactory;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<SentimentResponse> sentiment(@RequestBody SentimentRequest sentimentRequest) {
        logger.info(String.format("Processing sentiment for text: %s", sentimentRequest.getText()));
        OptionalDouble optionalDouble = sentimentProcessingService.forText(sentimentRequest.getText());
        return optionalDouble.isPresent() ?
                ok(sentimentResponseFactory.create(sentimentRequest, optionalDouble.getAsDouble())) :
                unprocessableEntity().body(null);
    }
}

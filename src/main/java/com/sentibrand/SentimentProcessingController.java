package com.sentibrand;

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
        OptionalDouble optionalDouble = sentimentProcessingService.forText(sentimentRequest.getText());
        return optionalDouble.isPresent() ?
                ok(sentimentResponseFactory.create(sentimentRequest, optionalDouble.getAsDouble())) :
                unprocessableEntity().body(null);
    }
}

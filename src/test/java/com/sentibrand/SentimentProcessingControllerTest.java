package com.sentibrand;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SentimentProcessingStarter.class)
public class SentimentProcessingControllerTest {

    @Autowired
    private SentimentProcessingController sentimentProcessingController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(sentimentProcessingController).build();
    }

    @Test
    public void shouldReturnOKAndPopulatedSentimentResponseJSONEntity() throws Exception {
        mockMvc.perform(post("/sentiment")
                .content(jsonResource("com/sentibrand/sentimentRequest.json"))
                .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResource("com/sentibrand/sentimentResponse.json"), true))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8));

    }

    @Test
    public void shouldReturnUnprocessableEntityAndEmptySentimentResponseJSONEntity() throws Exception {
        mockMvc.perform(post("/sentiment")
                .content(jsonResource("com/sentibrand/badSentimentRequest.json"))
                .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    private String jsonResource(String resourceLocation) throws IOException {
        return Resources.toString(getResource(resourceLocation), defaultCharset());
    }
}
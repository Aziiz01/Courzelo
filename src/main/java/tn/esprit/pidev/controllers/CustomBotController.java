package tn.esprit.pidev.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tn.esprit.pidev.dto.ChatGPTRequest;
import tn.esprit.pidev.dto.ChatGptResponse;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    private static final Logger logger = LoggerFactory.getLogger(CustomBotController.class); // Declare logger

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {
        try {
            ChatGPTRequest request = new ChatGPTRequest(model, prompt);
            ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
            return chatGptResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            logger.error("An error occurred while generating:", e);
            return "An error occurred. Please try again later.";
        }
    }
}

package tn.esprit.pidev.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {

    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system",
                "**Analyze the text and surrounding context of the user's input to determine if it contains any of the following harmful elements:**\n" +
                "\n" +
                "- **Offensive or discriminatory language:** This includes racial slurs, insults based on protected characteristics, and hate speech.\n" +
                "- **Threats of violence or harm:** This encompasses direct threats against individuals or groups, as well as implicit threats and intimidation.\n" +
                "- **Harassment or bullying:** This includes unwelcome or abusive behavior intended to upset, offend, or demean another person.\n" +
                "- **Sexually suggestive or explicit content:** This covers any language or depictions that are sexually suggestive in nature or explicitly graphic.\n" +
                "- **Spam or irrelevant content:** This includes unsolicited messages, promotional content, or text unrelated to the current conversation.\n" +
                "\n" +
                "**Based on your analysis, respond with one of the following:**\n" +
                "\n" +
                "- **\"valid\"** if the text and context are free from harmful elements.\n" +
                "- **\"not valid\"** if the text or context contains any of the identified harmful elements.\n" +
                "\n" +
                "**Remember to consider the overall tone and sentiment of the text, as well as any cultural nuances that might be relevant.**\n" +
                "\n" +
                "**Example:**\n" +
                "\n" +
                "**USER_INPUT:** You're such an idiot! I'm going to beat you up if you keep talking like that.\n" +
                "\n" +
                "**CONTEXT:** The user is responding to a disagreement in an online forum but with threatening tone and aggressive language.\n" +
                "\n" +
                "**RESPONSE:** not valid "));
        this.messages.add(new Message("user",prompt));
    }
}
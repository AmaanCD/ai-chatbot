package com.project.ai_demo.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements ChatServiceI {

    //Create chat client bean

    private final OllamaChatModel ollamaChatModel;

    public ChatService(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    @Override
    public String callAgent(String prompt) {

        ChatResponse chatResponse = null;
        try {
            chatResponse = ollamaChatModel.call(
                    new Prompt(prompt)
            );
        } catch (Exception e) {

            return "Error: " + e.fillInStackTrace();
        }
        // Return the content of the AssistantMessage as a string
        return chatResponse.getResult().getOutput().getText();
    }

}

package com.db.vector.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private org.springframework.ai.ollama.OllamaChatModel ollamaChatModel;

    @Override
    public String chat(String query) {

        ChatResponse chatResponse = null;
        try {
            chatResponse = ollamaChatModel.call(
                    new Prompt(query));
        } catch (Exception e) {

            return "Error: " + e.fillInStackTrace();
        }
        // Return the content of the AssistantMessage as a string
        return chatResponse.getResult().getOutput().getText();
    }

}

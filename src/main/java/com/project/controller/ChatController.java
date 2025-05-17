package com.project.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ChatController {

     //Create chat client bean
    @Autowired
    OllamaChatModel ollamaChatModel;

    @PutMapping("/chat")
    public String callAgent(@RequestParam("prompt") String prompt){
        ChatResponse chatResponse = null;
        try {
            chatResponse = ollamaChatModel.call(
                new Prompt(prompt)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        // Return the content of the AssistantMessage as a string
        return chatResponse.getResult().getOutput().getText();
    }
    
}
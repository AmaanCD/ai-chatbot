package com.db.vector.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.vector.service.ChatService;

@RestController
@RequestMapping("/chat/api/v1")
public class ChatController {

    @Autowired
    ChatService chatService;


    ChatClient chatClient;

    public ChatController(ChatService chatService, ChatClient.Builder chaBuilder) {
        this.chatService = chatService;
        this.chatClient = chaBuilder.build();

    }

    // Constructor injection of the ChatService

    @GetMapping("/ask")
    public String test(@RequestParam("query") String query) {

        return this.chatService.chat(query);

    }

    @GetMapping("/question")
    public String ask(@RequestParam("query")String query){
        return
                this.chatService.ask(query);
    }


}

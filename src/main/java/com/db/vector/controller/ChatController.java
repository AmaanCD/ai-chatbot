package com.db.vector.controller;

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

    // Constructor injection of the ChatService

    @GetMapping("/ask")
    public String test(@RequestParam("query") String query) {

        return this.chatService.chat(query);

    }

}

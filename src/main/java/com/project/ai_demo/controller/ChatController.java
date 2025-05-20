package com.project.ai_demo.controller;

import com.project.ai_demo.service.ChatServiceI;
import com.project.ai_demo.service.VectorDbService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ChatController {


    private final ChatServiceI chatServiceI;

    private final VectorDbService vectorDbService;

    public ChatController(ChatServiceI chatServiceI,VectorDbService vectorDbService) {
        this.chatServiceI = chatServiceI;
        this.vectorDbService = vectorDbService;
    }

    @PutMapping("/chat")
    public String callAgent(@RequestParam("prompt") String prompt){

        return chatServiceI.callAgent(prompt);
    
    }

//    @PostMapping(value = "/upload")
//    public void saveFile(@RequestParam("file") MultipartFile multipartFile){
//        this.saveFile(multipartFile);
//    }
//
//    public List<Document> getDocsByQuery(@RequestParam("query") String query){
//        return this.vectorDbService.query(query);
//    }

}
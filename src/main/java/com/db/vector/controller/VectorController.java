package com.db.vector.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.db.vector.service.VectorService;

@RestController
@RequestMapping("/vector/api/v1")
public class VectorController {

    @Autowired
    VectorService vectorService;

    @PostMapping(value = "/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        vectorService.upload(file);
    }

    @GetMapping(value = "/query")
    public List<Document> query(@RequestParam("query") String query) {
        return vectorService.query(query);
    }
}

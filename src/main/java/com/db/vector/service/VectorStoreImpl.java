package com.db.vector.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VectorStoreImpl implements VectorService {

    @Autowired
    private VectorStore vectorStore;

    private final TextSplitter textSplitter;

    public VectorStoreImpl() {
        // Initialize with default TokenTextSplitter
        this.textSplitter = new TokenTextSplitter();
        this.vectorStore = null;
    }

    @Override
    public void upload(MultipartFile multipartFile) throws IOException {
        // Create temporary file
        Path tempFile = Files.createFile(Path.of("upload-" + multipartFile.getOriginalFilename()));
        multipartFile.transferTo(tempFile);
        
        try {
            // Read document using Tika
            TikaDocumentReader documentReader = new TikaDocumentReader(tempFile.toUri().toString());
            List<Document> documents = documentReader.read();
            
            // Split each document into smaller chunks
            List<Document> chunkedDocuments = new ArrayList<>();
            for (Document doc : documents) {
                // Create a list with the single document
                List<Document> singleDoc = List.of(doc);
                // Split into chunks and add to our collection
                chunkedDocuments.addAll(textSplitter.apply(singleDoc));
            }
            
            // Store the chunked documents
            vectorStore.accept(chunkedDocuments);
        } finally {
            // Clean up temp file
            Files.deleteIfExists(tempFile);
        }
    }

    @Override
    public List<Document> query(String query) {
        // Implement the query logic here
        return vectorStore.similaritySearch(query);
    }

    @PutMapping(value = "add")
    public void test() {
        Document document = new Document("Hello world");
        vectorStore.add(List.of(document));
    }

    @GetMapping(value = "get")
    public String getData(@RequestParam("doc") String query) {
        List<Document> documents = vectorStore.similaritySearch(query);
        StringBuilder result = new StringBuilder();
        for (Document document : documents) {
            result.append(document.getFormattedContent()).append("\n");
        }
        return result.toString();
    }

}

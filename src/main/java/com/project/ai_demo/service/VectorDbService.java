package com.project.ai_demo.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class VectorDbService {

   private final VectorStore vectorStore;

    public VectorDbService(PgVectorStore pgVectorStore
                           ) {
        this.vectorStore= pgVectorStore;


    }

    public void saveFile(MultipartFile multipartFile) throws IOException {

      Path tempFile = Files.createFile(Path.of("upload-" + multipartFile.getOriginalFilename()),null);
      multipartFile.transferTo(tempFile);
       TikaDocumentReader documentReader = new TikaDocumentReader(tempFile.toUri().toString());
       List<Document> documents = documentReader.read();
       vectorStore.accept(documents);
    }

    public List<Document> query(String query){
        return vectorStore.similaritySearch(query);
    }



}


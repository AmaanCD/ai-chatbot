package com.db.vector.service;


import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

public interface VectorService {

    void upload(MultipartFile multipart) throws IOException;
    List<Document> query(String query);

}

package com.db.vector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Autowired
    private org.springframework.ai.ollama.OllamaChatModel ollamaChatModel;


    private final VectorStore vectorStore;

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder chaBuilder,VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.chatClient = chaBuilder.build().mutate().defaultAdvisors(
                QuestionAnswerAdvisor.builder(vectorStore).build()

        ).build();
    }

    public String ask(String query){

        return chatClient.prompt().user(query).call().content();
    }

    @Override
    public String chat(String query) {
        logger.info("Received chat query: {}", query);

        ChatResponse chatResponse = null;
        try {
            // Search for relevant documents
            logger.debug("Performing similarity search for query");
            List<Document> relevantDocs = List.of(vectorStore.similaritySearch(query).get(1));
            logger.info("Found {} relevant documents", relevantDocs.size());

            // Build prompt with context
            String promptWithContext = buildPromptWithContext(query, relevantDocs);
            logger.debug("Built prompt with context: {}", promptWithContext);

            // Call the model
            chatResponse = ollamaChatModel.call(new Prompt(promptWithContext));
            logger.info("Successfully received response from chat model");

            String response = chatResponse.getResult().getOutput().getText();
            logger.debug("Chat response: {}", response);
            return response;

        } catch (Exception e) {
            logger.error("Error processing chat request", e);
            return "Error: " + e.getMessage();
        }
    }

    private String buildPromptWithContext(String query, List<Document> documents) {
        logger.debug("Building prompt with {} documents", documents.size());

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(
                "You are a helpful AI assistant. Use the following context to answer questions. If the context isn't relevant, provide a general response.\n\n");

        if (!documents.isEmpty()) {
            promptBuilder.append("Context:\n");
            for (Document doc : documents) {
                promptBuilder.append("---\n")
                        .append(doc.getId())
                        .append(": ")
                        .append(doc.getText()) // Using getText() instead of getContent()
                        .append("\n");
            }
            promptBuilder.append("---\n\n");
        }

        promptBuilder.append("Question: ").append(query);

        String finalPrompt = promptBuilder.toString();
        logger.debug("Final prompt built: {}", finalPrompt);
        return finalPrompt;
    }
}

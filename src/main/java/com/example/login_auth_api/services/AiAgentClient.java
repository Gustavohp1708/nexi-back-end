package com.example.login_auth_api.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.login_auth_api.dto.ChatResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AiAgentClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${ai.agent.url:http://localhost:8000/chat}")
    private String aiAgentUrl;

    public ChatResponseDTO ask(String question, String sessionId) {
        try {
            String requestBody = objectMapper.writeValueAsString(
                    Map.of("question", question, "session_id", sessionId));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(aiAgentUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("AI agent returned status " + response.statusCode());
            }

            return objectMapper.readValue(response.body(), ChatResponseDTO.class);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Could not get a response from the AI agent.", exception);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not get a response from the AI agent.", exception);
        }
    }
}

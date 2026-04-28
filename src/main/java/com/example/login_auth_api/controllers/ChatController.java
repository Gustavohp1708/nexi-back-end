package com.example.login_auth_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login_auth_api.dto.ChatRequestDTO;
import com.example.login_auth_api.dto.ChatResponseDTO;
import com.example.login_auth_api.services.AiAgentClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AiAgentClient aiAgentClient;
    private static final String DEFAULT_SESSION_ID = "anonymous-session";

    @PostMapping
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO body, Authentication authentication) {
        String question = resolveQuestion(body);
        String sessionId = resolveSessionId(body, authentication);

        String prompt = authentication == null
                ? question
                : "Usuario autenticado: " + authentication.getName() + "\nPergunta: " + question;

        ChatResponseDTO response = aiAgentClient.ask(prompt, sessionId);
        return ResponseEntity.ok(response);
    }

    private String resolveQuestion(ChatRequestDTO body) {
        if (body.question() != null && !body.question().isBlank()) {
            return body.question().trim();
        }
        return body.message() == null ? "" : body.message().trim();
    }

    private String resolveSessionId(ChatRequestDTO body, Authentication authentication) {
        if (body.session_id() != null && !body.session_id().isBlank()) {
            return body.session_id().trim();
        }
        if (authentication != null && authentication.getName() != null && !authentication.getName().isBlank()) {
            return "user-" + authentication.getName().trim();
        }
        return DEFAULT_SESSION_ID;
    }
}

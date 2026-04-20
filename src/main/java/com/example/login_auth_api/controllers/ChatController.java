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

    @PostMapping
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO body, Authentication authentication) {
        String prompt = authentication == null
                ? body.message()
                : "Usuario autenticado: " + authentication.getName() + "\nPergunta: " + body.message();

        String answer = aiAgentClient.ask(prompt);
        return ResponseEntity.ok(new ChatResponseDTO(answer));
    }
}

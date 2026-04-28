package com.example.login_auth_api.dto;

import java.util.List;

public record ChatResponseDTO(String answer, List<String> suggestions) {
}

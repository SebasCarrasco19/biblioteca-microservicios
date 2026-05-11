package com.biblioteca.auth_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String message;
    private Long userId;
    private String email;
    private String token;
}
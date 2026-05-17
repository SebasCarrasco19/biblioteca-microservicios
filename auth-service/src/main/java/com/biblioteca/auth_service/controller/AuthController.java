package com.biblioteca.auth_service.controller;

import com.biblioteca.auth_service.dto.AuthResponse;
import com.biblioteca.auth_service.dto.LoginRequest;
import com.biblioteca.auth_service.dto.RegisterRequest;
import com.biblioteca.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrarCredenciales(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = service.registrarCredenciales(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }
}
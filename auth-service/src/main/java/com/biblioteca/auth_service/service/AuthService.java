package com.biblioteca.auth_service.service;

import com.biblioteca.auth_service.dto.AuthResponse;
import com.biblioteca.auth_service.dto.LoginRequest;
import com.biblioteca.auth_service.dto.RegisterRequest;

public interface AuthService {

    AuthResponse registrarCredenciales(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
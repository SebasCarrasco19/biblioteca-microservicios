package com.biblioteca.auth_service.controller;

import com.biblioteca.auth_service.dto.AuthResponse;
import com.biblioteca.auth_service.dto.LoginRequest;
import com.biblioteca.auth_service.dto.RegisterRequest;
import com.biblioteca.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones de registro de credenciales e inicio de sesión")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    @Operation(
            summary = "Registrar credenciales",
            description = "Registra las credenciales de acceso de un usuario y retorna la información de autenticación creada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Credenciales registradas correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de registro inválidos"),
            @ApiResponse(responseCode = "409", description = "Las credenciales ya se encuentran registradas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AuthResponse> registrarCredenciales(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = service.registrarCredenciales(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Valida las credenciales del usuario y retorna la información de autenticación, incluyendo el token cuando corresponde."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "400", description = "Solicitud de inicio de sesión inválida"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "403", description = "Usuario sin autorización o inactivo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }
}

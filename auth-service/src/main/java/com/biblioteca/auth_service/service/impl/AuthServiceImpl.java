package com.biblioteca.auth_service.service.impl;

import com.biblioteca.auth_service.client.UserClient;
import com.biblioteca.auth_service.dto.AuthResponse;
import com.biblioteca.auth_service.dto.LoginRequest;
import com.biblioteca.auth_service.dto.RegisterRequest;
import com.biblioteca.auth_service.dto.UserClientResponse;
import com.biblioteca.auth_service.exception.AuthException;
import com.biblioteca.auth_service.exception.CredentialNotFoundException;
import com.biblioteca.auth_service.exception.DuplicateCredentialException;
import com.biblioteca.auth_service.exception.RemoteUserNotFoundException;
import com.biblioteca.auth_service.exception.RemoteUserStateException;
import com.biblioteca.auth_service.model.Credential;
import com.biblioteca.auth_service.repository.CredentialRepository;
import com.biblioteca.auth_service.security.JwtService;
import com.biblioteca.auth_service.service.AuthService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse registrarCredenciales(RegisterRequest request) {
        log.info("Intentando registrar credenciales para usuario ID: {}", request.getUserId());

        if (credentialRepository.existsByEmail(request.getEmail())) {
            log.warn("No se pudo registrar credencial. Email duplicado: {}", request.getEmail());
            throw new DuplicateCredentialException("El email ya tiene credenciales registradas");
        }

        if (credentialRepository.existsByUserId(request.getUserId())) {
            log.warn("No se pudo registrar credencial. Usuario ID {} ya tiene credenciales", request.getUserId());
            throw new DuplicateCredentialException("El usuario ya tiene credenciales registradas");
        }

        UserClientResponse usuario;

        try {
            usuario = userClient.buscarUsuarioPorId(request.getUserId());
            log.info("Usuario encontrado en user-service con ID: {}", usuario.getId());
        } catch (FeignException.NotFound ex) {
            log.warn("Usuario no encontrado en user-service con ID: {}", request.getUserId());
            throw new RemoteUserNotFoundException("No existe usuario con ID: " + request.getUserId());
        }

        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            log.warn("No se pueden registrar credenciales. Usuario ID {} está inactivo", usuario.getId());
            throw new RemoteUserStateException("No se pueden registrar credenciales para un usuario inactivo");
        }

        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())) {
            log.warn("El email enviado no coincide con el email del usuario ID {}", usuario.getId());
            throw new AuthException("El email no coincide con el usuario indicado");
        }

        Credential credential = Credential.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .build();

        Credential guardada = credentialRepository.save(credential);

        String token = jwtService.generarToken(guardada);

        log.info("Credenciales registradas correctamente para usuario ID: {}", guardada.getUserId());

        return AuthResponse.builder()
                .message("Credenciales registradas correctamente")
                .userId(guardada.getUserId())
                .email(guardada.getEmail())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Intentando iniciar sesión con email: {}", request.getEmail());

        Credential credential = credentialRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("No se encontraron credenciales para email: {}", request.getEmail());
                    return new CredentialNotFoundException("No existen credenciales registradas para este email");
                });

        if (!"ACTIVO".equalsIgnoreCase(credential.getEstado())) {
            log.warn("No se puede iniciar sesión. Credencial inactiva para email: {}", request.getEmail());
            throw new AuthException("Las credenciales se encuentran inactivas");
        }

        if (!passwordEncoder.matches(request.getPassword(), credential.getPassword())) {
            log.warn("Contraseña incorrecta para email: {}", request.getEmail());
            throw new AuthException("Contraseña incorrecta");
        }

        UserClientResponse usuario;

        try {
            usuario = userClient.buscarUsuarioPorId(credential.getUserId());
            log.info("Usuario validado en user-service con ID: {}", usuario.getId());
        } catch (FeignException.NotFound ex) {
            log.warn("Usuario asociado a credenciales no existe en user-service. ID: {}", credential.getUserId());
            throw new RemoteUserNotFoundException("El usuario asociado a estas credenciales no existe");
        }

        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            log.warn("No se puede iniciar sesión. Usuario ID {} está inactivo", usuario.getId());
            throw new RemoteUserStateException("No se puede iniciar sesión con un usuario inactivo");
        }

        String token = jwtService.generarToken(credential);

        log.info("Login exitoso para usuario ID: {}", credential.getUserId());

        return AuthResponse.builder()
                .message("Login exitoso")
                .userId(credential.getUserId())
                .email(credential.getEmail())
                .token(token)
                .build();
    }
}
package com.biblioteca.auth_service.service.impl;

import com.biblioteca.auth_service.dto.RegisterRequest;
import com.biblioteca.auth_service.exception.DuplicateCredentialException;
import com.biblioteca.auth_service.repository.CredentialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private CredentialRepository credentialRepository;

    @InjectMocks
    private AuthServiceImpl service;

    @Test
    void registrarCredenciales_debeRechazarEmailDuplicado() {
        RegisterRequest request = new RegisterRequest();
        request.setUserId(1L);
        request.setEmail("seba@example.com");
        request.setPassword("secreto123");
        when(credentialRepository.existsByEmail("seba@example.com")).thenReturn(true);

        assertThrows(DuplicateCredentialException.class,
                () -> service.registrarCredenciales(request));
    }
}

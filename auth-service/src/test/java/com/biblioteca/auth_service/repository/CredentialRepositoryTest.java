package com.biblioteca.auth_service.repository;

import com.biblioteca.auth_service.model.Credential;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CredentialRepositoryTest {

    @Autowired
    private CredentialRepository repository;

    @Test
    void findByEmail_debeEncontrarCredencialGuardada() {
        repository.save(Credential.builder()
                .userId(1L)
                .email("auth@example.com")
                .password("hash")
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .build());

        assertTrue(repository.findByEmail("auth@example.com").isPresent());
        assertFalse(repository.findByEmail("otro@example.com").isPresent());
    }
}

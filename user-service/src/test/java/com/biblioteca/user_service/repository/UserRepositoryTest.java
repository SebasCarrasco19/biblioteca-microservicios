package com.biblioteca.user_service.repository;

import com.biblioteca.user_service.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void existsByEmail_debeEncontrarUsuarioGuardado() {
        repository.save(User.builder()
                .nombre("Ana")
                .apellido("Rojas")
                .email("ana@example.com")
                .telefono("911111111")
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .build());

        assertTrue(repository.existsByEmail("ana@example.com"));
        assertFalse(repository.existsByEmail("otro@example.com"));
    }
}

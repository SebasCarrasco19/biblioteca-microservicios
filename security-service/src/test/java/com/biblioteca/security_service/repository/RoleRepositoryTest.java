package com.biblioteca.security_service.repository;

import com.biblioteca.security_service.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private RoleRepository repository;

    @Test
    void existsByNombre_debeEncontrarRol() {
        repository.save(Role.builder().nombre("ADMIN").descripcion("Administrador")
                .estado("ACTIVO").fechaCreacion(LocalDateTime.now()).build());
        assertTrue(repository.existsByNombre("ADMIN"));
        assertFalse(repository.existsByNombre("OTRO"));
    }
}

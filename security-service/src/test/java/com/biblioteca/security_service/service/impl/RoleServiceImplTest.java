package com.biblioteca.security_service.service.impl;

import com.biblioteca.security_service.dto.RoleResponse;
import com.biblioteca.security_service.exception.RoleNotFoundException;
import com.biblioteca.security_service.model.Role;
import com.biblioteca.security_service.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleServiceImpl service;

    @Test
    void buscarRolPorId_debeRetornarRol() {
        Role role = Role.builder().id(1L).nombre("ADMIN").descripcion("Administrador")
                .estado("ACTIVO").fechaCreacion(LocalDateTime.now()).build();
        when(repository.findById(1L)).thenReturn(Optional.of(role));

        RoleResponse response = service.buscarRolPorId(1L);
        assertEquals("ADMIN", response.getNombre());
    }

    @Test
    void buscarRolPorId_debeLanzarExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class, () -> service.buscarRolPorId(99L));
    }
}

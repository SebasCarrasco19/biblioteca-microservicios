package com.biblioteca.user_service.service.impl;

import com.biblioteca.user_service.dto.UserResponse;
import com.biblioteca.user_service.exception.UserNotFoundException;
import com.biblioteca.user_service.model.User;
import com.biblioteca.user_service.repository.UserRepository;
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
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void buscarPorId_debeRetornarUsuarioCuandoExiste() {
        User user = User.builder()
                .id(1L)
                .nombre("Sebastián")
                .apellido("Pérez")
                .email("seba@example.com")
                .telefono("912345678")
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = service.buscarPorId(1L);

        assertEquals(1L, response.getId());
        assertEquals("seba@example.com", response.getEmail());
        assertEquals("ACTIVO", response.getEstado());
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.buscarPorId(99L));
    }
}

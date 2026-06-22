package com.example.category_service.ServiceCategory.impl;

import com.example.category_service.ModelCategory.ModelCategory;
import com.example.category_service.RepositoryCategory.RepositoryCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceCategoryTest {
    @Mock
    private RepositoryCategory repository;

    @Test
    void findById_debeRetornarCategoria() {
        ServiceCategory service = new ServiceCategory(repository);
        ModelCategory category = ModelCategory.builder().id(1L).nombre("Historia")
                .descripcion("Libros históricos").estado("ACTIVO").build();
        when(repository.findById(1L)).thenReturn(Optional.of(category));
        assertEquals("Historia", service.findById(1L).getNombre());
    }

    @Test
    void findById_debeLanzar404CuandoNoExiste() {
        ServiceCategory service = new ServiceCategory(repository);
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.findById(99L));
    }
}

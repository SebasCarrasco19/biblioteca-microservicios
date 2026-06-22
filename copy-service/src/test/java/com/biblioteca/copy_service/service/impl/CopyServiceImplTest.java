package com.biblioteca.copy_service.service.impl;

import com.biblioteca.copy_service.client.BookClient;
import com.biblioteca.copy_service.model.Copy;
import com.biblioteca.copy_service.model.CopyEstado;
import com.biblioteca.copy_service.model.CopyStatus;
import com.biblioteca.copy_service.repository.CopyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CopyServiceImplTest {
    @Mock private CopyRepository repository;
    @Mock private BookClient bookClient;

    @Test
    void isAvailable_debeRetornarTrueParaCopiaActivaDisponible() {
        CopyServiceImpl service = new CopyServiceImpl(repository, bookClient);
        Copy copy = new Copy();
        copy.setId(1L);
        copy.setEstado(CopyEstado.ACTIVO);
        copy.setStatus(CopyStatus.DISPONIBLE);
        when(repository.findById(1L)).thenReturn(Optional.of(copy));
        assertTrue(service.isAvailable(1L));
    }
}

package com.biblioteca.copy_service.repository;

import com.biblioteca.copy_service.model.Copy;
import com.biblioteca.copy_service.model.CopyEstado;
import com.biblioteca.copy_service.model.CopyStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CopyRepositoryTest {
    @Autowired private CopyRepository repository;

    @Test
    void findByIdAndEstado_debeEncontrarCopiaActiva() {
        Copy copy = new Copy();
        copy.setBookId(1L);
        copy.setInventoryCode("INV-001");
        copy.setLocation("Estante A");
        copy.setStatus(CopyStatus.DISPONIBLE);
        copy.setEstado(CopyEstado.ACTIVO);
        Copy saved = repository.save(copy);
        assertTrue(repository.findByIdAndEstado(saved.getId(), CopyEstado.ACTIVO).isPresent());
    }
}

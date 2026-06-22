package com.example.category_service.RepositoryCategory;

import com.example.category_service.ModelCategory.ModelCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RepositoryCategoryTest {
    @Autowired
    private RepositoryCategory repository;

    @Test
    void existsByNombreIgnoreCase_debeIgnorarMayusculas() {
        repository.save(ModelCategory.builder().nombre("Ciencia").descripcion("Divulgación")
                .estado("ACTIVO").build());
        assertTrue(repository.existsByNombreIgnoreCase("CIENCIA"));
    }
}

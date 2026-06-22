package com.example.fine_service.RepositoryFine;

import com.example.fine_service.ModelFine.FineStatus;
import com.example.fine_service.ModelFine.ModelFine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class RepositoryFineTest {

    @Autowired
    private RepositoryFine repository;

    @Test
    void findByStatus_debeEncontrarMultaPendiente() {

        ModelFine multa = ModelFine.builder()
                .userId(1L)
                .loanId(2L)
                .daysLate(3)
                .amount(3000.0)
                .reason("Atraso de préstamo")
                .paid(false)
                .status(FineStatus.PENDING)
                .build();

        repository.save(multa);

        assertEquals(
                1,
                repository.findByStatus(FineStatus.PENDING).size()
        );
    }
}

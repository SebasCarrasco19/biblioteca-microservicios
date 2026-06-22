package com.biblioteca.loan_service.service.impl;

import com.biblioteca.loan_service.model.Loan;
import com.biblioteca.loan_service.model.LoanStatus;
import com.biblioteca.loan_service.repository.LoanRepository;
import com.biblioteca.loan_service.dto.LoanResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {
    @Mock private LoanRepository repository;
    @InjectMocks private LoanServiceImpl service;

    @Test
    void getLoanById_debeRetornarPrestamo() {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setUserId(10L);
        loan.setCopyId(20L);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(7));
        loan.setStatus(LoanStatus.ACTIVO);
        loan.setCreatedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(loan));

        LoanResponse response = service.getLoanById(1L);
        assertEquals(10L, response.getUserId());
        assertEquals("ACTIVO", response.getStatus());
    }
}

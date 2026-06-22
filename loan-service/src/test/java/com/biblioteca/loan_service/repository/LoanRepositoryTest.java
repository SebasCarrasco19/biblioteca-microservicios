package com.biblioteca.loan_service.repository;

import com.biblioteca.loan_service.model.Loan;
import com.biblioteca.loan_service.model.LoanStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanRepositoryTest {
    @Autowired private LoanRepository repository;

    @Test
    void existsByCopyIdAndStatus_debeEncontrarPrestamoActivo() {
        Loan loan = new Loan();
        loan.setUserId(1L);
        loan.setCopyId(2L);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(7));
        loan.setStatus(LoanStatus.ACTIVO);
        repository.save(loan);
        assertTrue(repository.existsByCopyIdAndStatus(2L, LoanStatus.ACTIVO));
    }
}

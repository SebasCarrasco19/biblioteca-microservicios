package com.biblioteca.loan_service.repository;

import com.biblioteca.loan_service.model.Loan;
import com.biblioteca.loan_service.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long userId);

    List<Loan> findByCopyId(Long copyId);

    boolean existsByCopyIdAndStatus(Long copyId, LoanStatus status);
}
package com.biblioteca.loan_service.service;

import com.biblioteca.loan_service.dto.LoanRequest;
import com.biblioteca.loan_service.dto.LoanResponse;

import java.util.List;

public interface LoanService {

    LoanResponse createLoan(LoanRequest request, Long userId);

    List<LoanResponse> getLoans();

    LoanResponse getLoanById(Long id);

    List<LoanResponse> getLoansByUser(Long userId);

    LoanResponse returnLoan(Long id, Long userId);

    LoanResponse cancelLoan(Long id, Long userId);

    LoanResponse markLoanAsOverdue(Long id);
}
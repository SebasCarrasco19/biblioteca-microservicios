package com.biblioteca.loan_service.controller;

import com.biblioteca.loan_service.dto.LoanRequest;
import com.biblioteca.loan_service.dto.LoanResponse;
import com.biblioteca.loan_service.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService service;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(
            @Valid @RequestBody LoanRequest request,
            @RequestHeader("X-User-Id") Long userId) {

        LoanResponse response = service.createLoan(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getLoans() {
        return ResponseEntity.ok(service.getLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLoanById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponse>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getLoansByUser(userId));
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        LoanResponse response = service.returnLoan(id, userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<LoanResponse> cancelLoan(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        LoanResponse response = service.cancelLoan(id, userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/overdue")
    public ResponseEntity<LoanResponse> markLoanAsOverdue(@PathVariable Long id) {
        LoanResponse response = service.markLoanAsOverdue(id);
        return ResponseEntity.ok(response);
    }
}
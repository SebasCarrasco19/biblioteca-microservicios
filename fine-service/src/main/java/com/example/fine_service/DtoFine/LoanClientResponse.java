package com.example.fine_service.DtoFine;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanClientResponse {

    private Long id;
    private Long userId;
    private Long copyId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
}
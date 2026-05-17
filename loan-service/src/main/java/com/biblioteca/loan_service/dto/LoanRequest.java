package com.biblioteca.loan_service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El ID del ejemplar es obligatorio")
    private Long copyId;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @FutureOrPresent(message = "La fecha de vencimiento debe ser igual o posterior a la fecha actual")
    private LocalDate dueDate;
}
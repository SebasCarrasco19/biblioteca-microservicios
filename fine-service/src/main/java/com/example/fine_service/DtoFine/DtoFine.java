package com.example.fine_service.DtoFine;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DtoFine {

    @NotNull(message="El ID del usuario es obligatorio")
    private Long userId;

    @NotNull(message="El ID del préstamo es obligatorio")
    private Long loanId;

    @NotNull(message="Los días de atraso son obligatorios")
    @Min(value = 1, message="Debe existir al menos 1 día de atraso")
    private Integer daysLate;

    @NotBlank(message="La razón de la multa es obligatoria")
    @Size(min=5, max=200, message="la razon debe tener entre 5 y 200 caracteres")
    private String reason;
}

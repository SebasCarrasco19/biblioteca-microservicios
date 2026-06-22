package com.biblioteca.reservation_service.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
    @NotNull(message = "El id del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El id de la copia es obligatorio")
    private Long copyId;

    private LocalDateTime expirationDate;
}

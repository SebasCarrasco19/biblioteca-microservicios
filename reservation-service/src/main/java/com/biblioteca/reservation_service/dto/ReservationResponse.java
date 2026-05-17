package com.biblioteca.reservation_service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {
    private Long id;
    private Long userId;
    private Long copyId;
    private String status;
    private Boolean active;
    private LocalDateTime reservationDate;
    private LocalDateTime expirationDate;
}

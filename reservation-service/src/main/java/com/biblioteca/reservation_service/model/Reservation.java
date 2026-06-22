package com.biblioteca.reservation_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long copyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime reservationDate;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @PrePersist
    void onCreate() {
        if (status == null) status = ReservationStatus.ACTIVA;
        if (active == null) active = true;
        if (reservationDate == null) reservationDate = LocalDateTime.now();
        if (expirationDate == null) expirationDate = reservationDate.plusDays(1);
    }
}

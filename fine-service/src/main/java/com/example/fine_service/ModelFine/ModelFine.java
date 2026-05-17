package com.example.fine_service.ModelFine;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelFine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long loanId;

    @Column(nullable = false)
    private Integer daysLate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 200)
    private String reason;

    @Column(nullable = false)
    private Boolean paid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FineStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    private LocalDateTime cancelledAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.paid == null) {
            this.paid = false;
        }

        if (this.status == null) {
            this.status = FineStatus.PENDING;
        }
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
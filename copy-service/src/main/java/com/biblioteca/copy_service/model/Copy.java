package com.biblioteca.copy_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "copies")
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false, unique = true, length = 60)
    private String inventoryCode;

    @Column(nullable = false, length = 120)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CopyStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CopyEstado estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    void onCreate() {
        if (status == null) status = CopyStatus.DISPONIBLE;
        if (estado == null) estado = CopyEstado.ACTIVO;
        if (fechaRegistro == null) fechaRegistro = LocalDateTime.now();
    }
}

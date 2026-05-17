package com.example.category_service.ModelCategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void onCreate() {
        this.fechaCreacion = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = "ACTIVO";
        }
    }
}
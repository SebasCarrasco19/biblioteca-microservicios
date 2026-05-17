package com.biblioteca.security_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
}
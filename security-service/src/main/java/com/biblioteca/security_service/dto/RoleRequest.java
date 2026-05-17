package com.biblioteca.security_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequest {

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción del rol es obligatoria")
    private String descripcion;
}
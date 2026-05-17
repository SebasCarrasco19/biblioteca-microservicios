package com.biblioteca.loan_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserClientResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String estado;
    private LocalDateTime fechaCreacion;
}
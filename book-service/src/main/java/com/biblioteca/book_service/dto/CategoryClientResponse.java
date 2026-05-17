package com.biblioteca.book_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryClientResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
}
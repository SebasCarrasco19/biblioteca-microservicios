package com.example.fine_service.DtoFine;

import lombok.Data;

@Data
public class UserClientResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String estado;
}
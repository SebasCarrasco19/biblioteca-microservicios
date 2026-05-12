package com.biblioteca.copy_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopyRequest {
    @NotNull(message = "El id del libro es obligatorio")
    private Long bookId;

    @NotBlank(message = "El codigo de inventario es obligatorio")
    @Size(max = 60, message = "El codigo no puede superar 60 caracteres")
    private String inventoryCode;

    @NotBlank(message = "La ubicacion es obligatoria")
    @Size(max = 120, message = "La ubicacion no puede superar 120 caracteres")
    private String location;
}

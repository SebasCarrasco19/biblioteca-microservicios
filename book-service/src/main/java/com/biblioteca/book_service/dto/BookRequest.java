package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    private String title;

    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(
            regexp = "^(\\d{9}(\\d|X)|97(8|9)\\d{10})$",
            message = "El ISBN debe tener un formato válido"
    )
    private String isbn;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 120, message = "El autor no puede superar los 120 caracteres")
    private String author;

    @NotBlank(message = "La editorial es obligatoria")
    @Size(max = 120, message = "La editorial no puede superar los 120 caracteres")
    private String editorial;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoryId;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1450, message = "El año de publicación debe ser mayor o igual a 1450")
    @Max(value = 2100, message = "El año de publicación debe ser menor o igual a 2100")
    private Integer publishedYear;
}

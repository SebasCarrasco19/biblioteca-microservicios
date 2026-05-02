package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 150, message = "El titulo no puede superar 150 caracteres")
    private String title;

    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "El ISBN debe tener formato valido ISBN-10 o ISBN-13")
    private String isbn;

    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String description;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 120, message = "El autor no puede superar 120 caracteres")
    private String author;

    @NotBlank(message = "La categoria es obligatoria")
    @Size(max = 80, message = "La categoria no puede superar 80 caracteres")
    private String category;

    @Min(value = 1450, message = "El anio de publicacion no es valido")
    @Max(value = 2100, message = "El anio de publicacion no es valido")
    private Integer publishedYear;
}

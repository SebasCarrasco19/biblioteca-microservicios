package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title max length is 150")
    private String title;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "ISBN format is invalid")
    private String isbn;

    @Size(max = 500, message = "Description max length is 500")
    private String description;

    @NotBlank(message = "Author is required")
    @Size(max = 120, message = "Author max length is 120")
    private String author;

    @NotBlank(message = "Editorial is required")
    @Size(max = 120, message = "Editorial max length is 120")
    private String editorial;

    @NotNull(message = "CategoryId is required")
    private Long categoryId;

    @NotNull(message = "Published year is required")
    @Min(value = 1450, message = "Published year is invalid")
    @Max(value = 2100, message = "Published year is invalid")
    private Integer publishedYear;
}

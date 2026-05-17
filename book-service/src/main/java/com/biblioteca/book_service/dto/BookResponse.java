package com.biblioteca.book_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String isbn;
    private String description;
    private String author;
    private String editorial;
    private Long categoryId;
    private Integer publishedYear;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

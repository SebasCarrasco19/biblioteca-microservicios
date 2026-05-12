package com.biblioteca.book_service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
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

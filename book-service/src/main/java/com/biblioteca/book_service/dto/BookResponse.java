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
    private String category;
    private Integer publishedYear;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.biblioteca.copy_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookClientResponse {
    private Long id;
    private String title;
    private String isbn;
    private Long categoryId;
    private String status;
    private String estado;
}

package com.biblioteca.book_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleValidationResponse {

    private boolean allowed;
    private String role;
    private String message;
}

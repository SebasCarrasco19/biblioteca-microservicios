package com.biblioteca.loan_service.dto;

import lombok.Data;

@Data
public class CopyClientResponse {

    private Long id;
    private Long bookId;
    private String inventoryCode;
    private String status;
    private Boolean active;
}
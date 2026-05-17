package com.biblioteca.copy_service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CopyResponse {
    private Long id;
    private Long bookId;
    private String inventoryCode;
    private String location;
    private String status;
    private String estado;
    private LocalDateTime fechaRegistro;
}

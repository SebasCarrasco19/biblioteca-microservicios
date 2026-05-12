package com.biblioteca.book_service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}

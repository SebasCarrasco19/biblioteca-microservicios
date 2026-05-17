package com.biblioteca.notification_service.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiError {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
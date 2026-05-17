package com.biblioteca.notification_service.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotificationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .status(404)
                        .error("NOT_FOUND")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(InvalidNotificationStateException.class)
    public ResponseEntity<ApiError> handleConflict(InvalidNotificationStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiError.builder()
                        .status(409)
                        .error("CONFLICT")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ApiError> handleService(UserServiceException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiError.builder()
                        .status(503)
                        .error("SERVICE_UNAVAILABLE")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(ApiError.builder()
                        .status(400)
                        .error("BAD_REQUEST")
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
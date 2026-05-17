package com.biblioteca.notification_service.dto;

import com.biblioteca.notification_service.model.NotificationType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotNull(message = "El userId es obligatorio")
    private Long userId;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 5, max = 300)
    private String message;

    @NotNull(message = "El tipo es obligatorio")
    private NotificationType type;
}
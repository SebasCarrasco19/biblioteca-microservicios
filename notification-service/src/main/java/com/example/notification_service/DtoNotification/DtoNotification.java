package com.example.notification_service.DtoNotification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DtoNotification {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 5, max = 255)
    private String message;

    @NotBlank(message = "El tipo es obligatorio")
    private String type;
}
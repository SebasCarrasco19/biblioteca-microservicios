package com.biblioteca.notification_service.dto;

import com.biblioteca.notification_service.model.NotificationStatus;
import com.biblioteca.notification_service.model.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long id;
    private Long userId;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private LocalDateTime updatedAt;
}
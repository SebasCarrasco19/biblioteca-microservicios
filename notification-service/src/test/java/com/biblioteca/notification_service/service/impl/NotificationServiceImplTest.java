package com.biblioteca.notification_service.service.impl;

import com.biblioteca.notification_service.client.UserClient;
import com.biblioteca.notification_service.dto.NotificationResponse;
import com.biblioteca.notification_service.model.Notification;
import com.biblioteca.notification_service.model.NotificationStatus;
import com.biblioteca.notification_service.model.NotificationType;
import com.biblioteca.notification_service.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {
    @Mock private NotificationRepository repository;
    @Mock private UserClient userClient;

    @Test
    void getById_debeRetornarNotificacion() {
        NotificationServiceImpl service = new NotificationServiceImpl(repository, userClient);
        Notification notification = Notification.builder().id(1L).userId(2L).title("Aviso")
                .message("Mensaje de prueba").type(NotificationType.INFO)
                .status(NotificationStatus.PENDING).createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        when(repository.findById(1L)).thenReturn(Optional.of(notification));
        NotificationResponse response = service.getById(1L);
        assertEquals("Aviso", response.getTitle());
    }
}

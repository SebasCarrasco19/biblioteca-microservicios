package com.biblioteca.notification_service.repository;

import com.biblioteca.notification_service.model.Notification;
import com.biblioteca.notification_service.model.NotificationStatus;
import com.biblioteca.notification_service.model.NotificationType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository repository;

    @Test
    void findByUserId_debeEncontrarNotificacionesDelUsuario() {

        Notification notificacion = Notification.builder()
                .userId(10L)
                .title("Aviso")
                .message("Mensaje de prueba")
                .type(NotificationType.INFO)
                .status(NotificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(notificacion);

        assertEquals(
                1,
                repository.findByUserId(10L).size()
        );
    }
}


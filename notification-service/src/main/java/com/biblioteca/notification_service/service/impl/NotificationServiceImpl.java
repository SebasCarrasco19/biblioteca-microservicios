package com.biblioteca.notification_service.service.impl;

import com.biblioteca.notification_service.client.UserClient;
import com.biblioteca.notification_service.dto.*;
import com.biblioteca.notification_service.exception.*;
import com.biblioteca.notification_service.model.*;
import com.biblioteca.notification_service.repository.NotificationRepository;
import com.biblioteca.notification_service.service.NotificationService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final UserClient userClient;

    @Override
    public NotificationResponse create(NotificationRequest request) {

        try {
            UserClientResponse user = userClient.getUserById(request.getUserId());

            if (!"ACTIVO".equalsIgnoreCase(user.getEstado())) {
                throw new UserServiceException("El usuario no está activo");
            }

        } catch (FeignException ex) {
            throw new UserServiceException("No se pudo validar el usuario");
        }

        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .status(NotificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return map(repository.save(notification));
    }

    @Override
    public List<NotificationResponse> getAll() {
        return repository.findAll().stream().map(this::map).toList();
    }

    @Override
    public NotificationResponse getById(Long id) {
        return map(find(id));
    }

    @Override
    public List<NotificationResponse> getByUserId(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public NotificationResponse send(Long id) {

        Notification notification = find(id);

        if (notification.getStatus() == NotificationStatus.SENT) {
            throw new InvalidNotificationStateException("La notificación ya fue enviada");
        }

        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        return map(repository.save(notification));
    }

    @Override
    public NotificationResponse read(Long id) {

        Notification notification = find(id);

        if (notification.getStatus() == NotificationStatus.CANCELLED) {
            throw new InvalidNotificationStateException("La notificación está cancelada");
        }

        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        return map(repository.save(notification));
    }

    @Override
    public MessageResponse cancel(Long id) {

        Notification notification = find(id);

        notification.setStatus(NotificationStatus.CANCELLED);
        notification.setUpdatedAt(LocalDateTime.now());

        repository.save(notification);

        return new MessageResponse("Notificación cancelada correctamente");
    }

    private Notification find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notificación no encontrada"));
    }

    private NotificationResponse map(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType())
                .status(n.getStatus())
                .createdAt(n.getCreatedAt())
                .sentAt(n.getSentAt())
                .readAt(n.getReadAt())
                .updatedAt(n.getUpdatedAt())
                .build();
    }
}
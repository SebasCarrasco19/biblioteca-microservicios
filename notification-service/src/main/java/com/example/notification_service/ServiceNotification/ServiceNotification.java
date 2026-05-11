package com.example.notification_service.ServiceNotification;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.notification_service.DtoNotification.DtoNotification;
import com.example.notification_service.ModelNotification.ModelNotification;
import com.example.notification_service.RepositoryNotification.RepositoryNotification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class ServiceNotification implements INotificationService {

    private final RepositoryNotification repositoryNotification;

    @Override
    public List<ModelNotification> findAll() {

        log.info("Obteniendo todas las notificaciones");

        return repositoryNotification.findAll();
    }

    @Override
    public ModelNotification findById(Long id) {

        log.info("Buscando notificación con ID {}", id);

        return repositoryNotification.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
    }

    @Override
    public List<ModelNotification> findByUserId(Long userId) {

        log.info("Buscando notificaciones del usuario {}", userId);

        return repositoryNotification.findByUserId(userId);
    }

    @Override
    public ModelNotification save(DtoNotification dtoNotification) {

        log.info("Creando notificación");

        ModelNotification notification = ModelNotification.builder()
                .userId(dtoNotification.getUserId())
                .message(dtoNotification.getMessage())
                .type(dtoNotification.getType())
                .sent(false)
                .createdAt(LocalDateTime.now())
                .build();

        return repositoryNotification.save(notification);
    }

    @Override
    public ModelNotification markAsSent(Long id) {

        log.info("Marcando notificación {} como enviada", id);

        ModelNotification notification = findById(id);

        notification.setSent(true);

        return repositoryNotification.save(notification);
    }

    @Override
    public void delete(Long id) {

        log.info("Eliminando notificación {}", id);

        repositoryNotification.deleteById(id);
    }
}
package com.example.notification_service.ServiceNotification;

import java.util.List;

import com.example.notification_service.DtoNotification.DtoNotification;
import com.example.notification_service.ModelNotification.ModelNotification;

public interface INotificationService {

    List<ModelNotification> findAll();

    ModelNotification findById(Long id);

    List<ModelNotification> findByUserId(Long userId);

    ModelNotification save(DtoNotification dtoNotification);

    ModelNotification markAsSent(Long id);

    void delete(Long id);
}
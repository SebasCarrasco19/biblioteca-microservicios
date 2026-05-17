package com.biblioteca.notification_service.service;

import com.biblioteca.notification_service.dto.*;

import java.util.List;

public interface NotificationService {

    NotificationResponse create(NotificationRequest request);

    List<NotificationResponse> getAll();

    NotificationResponse getById(Long id);

    List<NotificationResponse> getByUserId(Long userId);

    NotificationResponse send(Long id);

    NotificationResponse read(Long id);

    MessageResponse cancel(Long id);
}
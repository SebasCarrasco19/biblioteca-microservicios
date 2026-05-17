package com.biblioteca.notification_service.controller;

import com.biblioteca.notification_service.dto.*;
import com.biblioteca.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public NotificationResponse create(@Valid @RequestBody NotificationRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<NotificationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public NotificationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PatchMapping("/{id}/send")
    public NotificationResponse send(@PathVariable Long id) {
        return service.send(id);
    }

    @PatchMapping("/{id}/read")
    public NotificationResponse read(@PathVariable Long id) {
        return service.read(id);
    }

    @DeleteMapping("/{id}")
    public MessageResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}
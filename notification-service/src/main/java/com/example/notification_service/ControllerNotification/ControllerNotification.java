package com.example.notification_service.ControllerNotification;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification_service.DtoNotification.DtoNotification;
import com.example.notification_service.ModelNotification.ModelNotification;
import com.example.notification_service.ServiceNotification.INotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/notifications")

@RequiredArgsConstructor
@Slf4j

public class ControllerNotification {

    private final INotificationService serviceNotification;

    @GetMapping
    public ResponseEntity<List<ModelNotification>> getAllNotifications() {

        log.info("GET /notifications");

        return ResponseEntity.ok(serviceNotification.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelNotification> getNotificationById(@PathVariable Long id) {

        log.info("GET /notifications/{}", id);

        return ResponseEntity.ok(serviceNotification.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ModelNotification>> getNotificationsByUserId(@PathVariable Long userId) {

        log.info("GET /notifications/user/{}", userId);

        return ResponseEntity.ok(serviceNotification.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ModelNotification> createNotification(
            @Valid @RequestBody DtoNotification dtoNotification) {

        log.info("POST /notifications");

        return ResponseEntity.ok(serviceNotification.save(dtoNotification));
    }

    @PutMapping("/{id}/send")
    public ResponseEntity<ModelNotification> markAsSent(@PathVariable Long id) {

        log.info("PUT /notifications/{}/send", id);

        return ResponseEntity.ok(serviceNotification.markAsSent(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {

        log.info("DELETE /notifications/{}", id);

        serviceNotification.delete(id);

        return ResponseEntity.ok("Notificación eliminada correctamente");
    }
}
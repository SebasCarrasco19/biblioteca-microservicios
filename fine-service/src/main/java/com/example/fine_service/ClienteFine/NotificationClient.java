package com.example.fine_service.ClienteFine;

import com.example.fine_service.DtoFine.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/api/notifications")
    void createNotification(@RequestBody NotificationRequest request);
}
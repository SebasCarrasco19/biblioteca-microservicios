package com.biblioteca.notification_service.client;

import com.biblioteca.notification_service.dto.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserClientResponse getUserById(@PathVariable Long id);
}
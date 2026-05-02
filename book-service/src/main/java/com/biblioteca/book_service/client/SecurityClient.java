package com.biblioteca.book_service.client;

import com.biblioteca.book_service.dto.RoleValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "security-service")
public interface SecurityClient {

    @GetMapping("/api/security/authorize")
    RoleValidationResponse authorize(@RequestParam("userId") Long userId, @RequestParam("action") String action);
}

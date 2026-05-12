package com.biblioteca.book_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "security-service")
public interface SecurityClient {

    @GetMapping("/api/user-roles/validate/{userId}/{roleName}")
    Boolean validateRole(@PathVariable("userId") Long userId, @PathVariable("roleName") String roleName);
}

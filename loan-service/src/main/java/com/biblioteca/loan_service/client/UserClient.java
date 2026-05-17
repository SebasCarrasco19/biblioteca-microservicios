package com.biblioteca.loan_service.client;

import com.biblioteca.loan_service.dto.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserClientResponse findById(@PathVariable("id") Long id);
}
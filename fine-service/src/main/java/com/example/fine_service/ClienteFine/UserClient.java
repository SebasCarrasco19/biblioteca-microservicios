package com.example.fine_service.ClienteFine;

import com.example.fine_service.DtoFine.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserClientResponse findById(@PathVariable("id") Long id);
}
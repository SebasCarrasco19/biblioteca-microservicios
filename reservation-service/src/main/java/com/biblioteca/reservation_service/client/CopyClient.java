package com.biblioteca.reservation_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "copy-service")
public interface CopyClient {

    @PostMapping("/api/copies/{id}/reserve")
    void reserve(@PathVariable("id") Long copyId);

    @PostMapping("/api/copies/{id}/release")
    void release(@PathVariable("id") Long copyId);
}

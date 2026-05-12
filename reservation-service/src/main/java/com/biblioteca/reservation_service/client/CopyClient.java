package com.biblioteca.reservation_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "copy-service")
public interface CopyClient {
    @PatchMapping("/api/copies/{id}/reserve")
    void reserve(@PathVariable("id") Long copyId);

    @PatchMapping("/api/copies/{id}/release")
    void release(@PathVariable("id") Long copyId);
}

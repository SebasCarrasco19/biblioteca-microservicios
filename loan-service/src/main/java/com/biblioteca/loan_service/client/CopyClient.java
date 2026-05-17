package com.biblioteca.loan_service.client;

import com.biblioteca.loan_service.dto.CopyClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "copy-service")
public interface CopyClient {

    @GetMapping("/api/copies/{id}")
    CopyClientResponse findById(@PathVariable("id") Long id);

    @PatchMapping("/api/copies/{id}/borrow")
    void markAsLoaned(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") Long userId
    );

    @PatchMapping("/api/copies/{id}/return")
    void markAsAvailable(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") Long userId
    );
}
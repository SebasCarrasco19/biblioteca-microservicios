package com.biblioteca.copy_service.controller;

import com.biblioteca.copy_service.dto.AvailabilityResponse;
import com.biblioteca.copy_service.dto.CopyRequest;
import com.biblioteca.copy_service.dto.CopyResponse;
import com.biblioteca.copy_service.dto.MessageResponse;
import com.biblioteca.copy_service.service.CopyService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/copies")
@RequiredArgsConstructor
public class CopyController {

    private final CopyService service;

    @PostMapping
    public ResponseEntity<CopyResponse> createCopy(@Valid @RequestBody CopyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCopy(request));
    }

    @GetMapping
    public ResponseEntity<List<CopyResponse>> getCopies() {
        return ResponseEntity.ok(service.getCopies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CopyResponse> getCopyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCopyById(id));
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<AvailabilityResponse> available(@PathVariable Long id) {
        return ResponseEntity.ok(new AvailabilityResponse(service.isAvailable(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CopyResponse> updateCopy(@PathVariable Long id, @Valid @RequestBody CopyRequest request) {
        return ResponseEntity.ok(service.updateCopy(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deactivateCopy(@PathVariable Long id) {
        service.deactivateCopy(id);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Copy deactivated successfully. ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<CopyResponse> activateCopy(@PathVariable Long id) {
        return ResponseEntity.ok(service.activateCopy(id));
    }

    @PatchMapping("/{id}/reserve")
    public ResponseEntity<CopyResponse> reserveCopy(@PathVariable Long id) {
        return ResponseEntity.ok(service.reserveCopy(id));
    }

    @PatchMapping("/{id}/release")
    public ResponseEntity<CopyResponse> releaseCopy(@PathVariable Long id) {
        return ResponseEntity.ok(service.releaseCopy(id));
    }

    @PatchMapping("/{id}/borrow")
    public ResponseEntity<CopyResponse> borrowCopy(@PathVariable Long id) {
        return ResponseEntity.ok(service.borrowCopy(id));
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<CopyResponse> returnCopy(@PathVariable Long id) {
        return ResponseEntity.ok(service.returnCopy(id));
    }
    @PostMapping("/{id}/reserve")
    public ResponseEntity<CopyResponse> reserveCopyPost(@PathVariable Long id) {
    return ResponseEntity.ok(service.reserveCopy(id));
}

    @PostMapping("/{id}/release")
    public ResponseEntity<CopyResponse> releaseCopyPost(@PathVariable Long id) {
    return ResponseEntity.ok(service.releaseCopy(id));
}
}

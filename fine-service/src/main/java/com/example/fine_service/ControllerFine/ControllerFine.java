package com.example.fine_service.ControllerFine;

import com.example.fine_service.DtoFine.DtoFine;
import com.example.fine_service.DtoFine.MessageResponse;
import com.example.fine_service.ModelFine.ModelFine;
import com.example.fine_service.ServiceFine.IFineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/fines")
@RequiredArgsConstructor
@Slf4j
public class ControllerFine {

    private final IFineService serviceFine;

    @GetMapping
    public ResponseEntity<List<ModelFine>> getAllFines() {
        log.info("GET /api/fines");
        return ResponseEntity.ok(serviceFine.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelFine> getFineById(@PathVariable Long id) {
        log.info("GET /api/fines/{}", id);
        return ResponseEntity.ok(serviceFine.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ModelFine>> getFinesByUserId(@PathVariable Long userId) {
        log.info("GET /api/fines/user/{}", userId);
        return ResponseEntity.ok(serviceFine.findByUserId(userId));
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<ModelFine>> getFinesByLoanId(@PathVariable Long loanId) {
        log.info("GET /api/fines/loan/{}", loanId);
        return ResponseEntity.ok(serviceFine.findByLoanId(loanId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ModelFine>> getPendingFines() {
        log.info("GET /api/fines/pending");
        return ResponseEntity.ok(serviceFine.findPendingFines());
    }

    @PostMapping
    public ResponseEntity<ModelFine> createFine(@Valid @RequestBody DtoFine dtoFine) {
        log.info("POST /api/fines");
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceFine.save(dtoFine));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<ModelFine> payFine(@PathVariable Long id) {
        log.info("PUT /api/fines/{}/pay", id);
        return ResponseEntity.ok(serviceFine.payFine(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> cancelFine(@PathVariable Long id) {
        log.info("DELETE /api/fines/{}", id);

        serviceFine.cancelFine(id);

        return ResponseEntity.ok(MessageResponse.builder()
                .message("Multa cancelada correctamente con ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
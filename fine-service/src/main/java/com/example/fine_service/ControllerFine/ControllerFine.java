package com.example.fine_service.ControllerFine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fine_service.DtoFine.DtoFine;
import com.example.fine_service.ModelFine.ModelFine;
import com.example.fine_service.ServiceFine.IFineService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
@Slf4j
public class ControllerFine {

    private final IFineService serviceFine;

    @GetMapping
    public ResponseEntity<List<ModelFine>> getAllFines() {
        log.info("GET /fines");
        return ResponseEntity.ok(serviceFine.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelFine> getFineById(@PathVariable Long id) {
        log.info("GET /fines/{}", id);
        return ResponseEntity.ok(serviceFine.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ModelFine>> getFinesByUserId(@PathVariable Long userId) {
        log.info("GET /fines/user/{}", userId);
        return ResponseEntity.ok(serviceFine.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ModelFine> createFine(@Valid @RequestBody DtoFine dtoFine) {
        log.info("POST /fines");
        return ResponseEntity.ok(serviceFine.save(dtoFine));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<ModelFine> payFine(@PathVariable Long id) {
        log.info("PUT /fines/{}/pay", id);
        return ResponseEntity.ok(serviceFine.payFine(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFine(@PathVariable Long id) {
        log.info("DELETE /fines/{}", id);
        serviceFine.delete(id);
        return ResponseEntity.ok("Multa eliminada correctamente");
    }
}
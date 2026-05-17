package com.example.fine_service.ClienteFine;

import com.example.fine_service.DtoFine.LoanClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "loan-service")
public interface LoanClient {

    @GetMapping("/api/loans/{id}")
    LoanClientResponse findById(@PathVariable("id") Long id);
}
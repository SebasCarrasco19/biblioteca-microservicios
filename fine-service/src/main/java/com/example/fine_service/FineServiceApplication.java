package com.example.fine_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FineServiceApplication.class, args);
    }
}
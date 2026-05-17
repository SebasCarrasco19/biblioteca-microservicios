package com.example.fine_service.DtoFine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

    private Long userId;
    private String title;
    private String message;
    private String type;
}
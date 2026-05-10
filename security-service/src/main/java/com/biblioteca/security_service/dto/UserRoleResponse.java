package com.biblioteca.security_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleResponse {

    private Long id;
    private Long userId;
    private Long roleId;
    private String estado;
    private LocalDateTime fechaAsignacion;
}

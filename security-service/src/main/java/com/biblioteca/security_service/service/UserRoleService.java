package com.biblioteca.security_service.service;

import com.biblioteca.security_service.dto.UserRoleRequest;
import com.biblioteca.security_service.dto.UserRoleResponse;

import java.util.List;

public interface UserRoleService {

    UserRoleResponse asignarRolAUsuario(UserRoleRequest request);

    List<UserRoleResponse> listarRolesPorUsuario(Long userId);

    boolean validarRolUsuario(Long userId, String roleName);

    void desactivarAsignacion(Long id);
}
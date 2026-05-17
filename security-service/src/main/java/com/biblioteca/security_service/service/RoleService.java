package com.biblioteca.security_service.service;

import com.biblioteca.security_service.dto.RoleRequest;
import com.biblioteca.security_service.dto.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse crearRol(RoleRequest request);

    List<RoleResponse> listarRoles();

    RoleResponse buscarRolPorId(Long id);

    RoleResponse actualizarRol(Long id, RoleRequest request);

    void desactivarRol(Long id);

    RoleResponse activarRol(Long id);
}
package com.biblioteca.security_service.service.impl;

import com.biblioteca.security_service.dto.RoleRequest;
import com.biblioteca.security_service.dto.RoleResponse;
import com.biblioteca.security_service.exception.DuplicateRoleException;
import com.biblioteca.security_service.exception.RoleNotFoundException;
import com.biblioteca.security_service.model.Role;
import com.biblioteca.security_service.repository.RoleRepository;
import com.biblioteca.security_service.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.security_service.exception.RoleStateException;


import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public RoleResponse crearRol(RoleRequest request) {
        log.info("Intentando crear rol con nombre: {}", request.getNombre());

        if (repository.existsByNombre(request.getNombre())) {
            log.warn("No se pudo crear rol. Nombre duplicado: {}", request.getNombre());
            throw new DuplicateRoleException("El rol ya está registrado");
        }

        Role role = Role.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .build();

        Role guardado = repository.save(role);

        log.info("Rol creado correctamente con ID: {}", guardado.getId());

        return convertirAResponse(guardado);
    }

    @Override
    public List<RoleResponse> listarRoles() {
        log.info("Listando todos los roles");

        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public RoleResponse buscarRolPorId(Long id) {
        log.info("Buscando rol con ID: {}", id);

        Role role = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rol no encontrado con ID: {}", id);
                    return new RoleNotFoundException("Rol no encontrado con ID: " + id);
                });

        return convertirAResponse(role);
    }

    @Override
    public RoleResponse actualizarRol(Long id, RoleRequest request) {
        log.info("Actualizando rol con ID: {}", id);

        Role role = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se pudo actualizar. Rol no encontrado con ID: {}", id);
                    return new RoleNotFoundException("Rol no encontrado con ID: " + id);
                });

        if (repository.existsByNombreAndIdNot(request.getNombre(), id)) {
            log.warn("No se pudo actualizar. Nombre de rol ya usado: {}", request.getNombre());
            throw new DuplicateRoleException("El nombre del rol ya está registrado por otro rol");
        }

        role.setNombre(request.getNombre());
        role.setDescripcion(request.getDescripcion());

        Role actualizado = repository.save(role);

        log.info("Rol actualizado correctamente con ID: {}", actualizado.getId());

        return convertirAResponse(actualizado);
    }

    @Override
    public void desactivarRol(Long id) {
        log.info("Desactivando rol con ID: {}", id);

        Role role = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se pudo desactivar. Rol no encontrado con ID: {}", id);
                    return new RoleNotFoundException("Rol no encontrado con ID: " + id);
                });

        if ("INACTIVO".equalsIgnoreCase(role.getEstado())) {
            log.warn("No se pudo desactivar. El rol con ID {} ya está inactivo", id);
            throw new RoleStateException("El rol ya está inactivo");
        }

        role.setEstado("INACTIVO");
        repository.save(role);

        log.info("Rol desactivado correctamente con ID: {}", id);
    }

    @Override
    public RoleResponse activarRol(Long id) {
        log.info("Activando rol con ID: {}", id);

        Role role = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se pudo activar. Rol no encontrado con ID: {}", id);
                    return new RoleNotFoundException("Rol no encontrado con ID: " + id);
                });

        if ("ACTIVO".equalsIgnoreCase(role.getEstado())) {
            log.warn("No se pudo activar. El rol con ID {} ya está activo", id);
            throw new RoleStateException("El rol ya está activo");
        }

        role.setEstado("ACTIVO");

        Role actualizado = repository.save(role);

        log.info("Rol activado correctamente con ID: {}", id);

        return convertirAResponse(actualizado);
    }

    private RoleResponse convertirAResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .nombre(role.getNombre())
                .descripcion(role.getDescripcion())
                .estado(role.getEstado())
                .fechaCreacion(role.getFechaCreacion())
                .build();
    }
}
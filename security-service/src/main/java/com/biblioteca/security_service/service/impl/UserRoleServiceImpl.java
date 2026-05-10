package com.biblioteca.security_service.service.impl;

import com.biblioteca.security_service.client.UserClient;
import com.biblioteca.security_service.dto.UserClientResponse;
import com.biblioteca.security_service.dto.UserRoleRequest;
import com.biblioteca.security_service.dto.UserRoleResponse;
import com.biblioteca.security_service.exception.DuplicateUserRoleException;
import com.biblioteca.security_service.exception.RemoteUserNotFoundException;
import com.biblioteca.security_service.exception.RoleNotFoundException;
import com.biblioteca.security_service.exception.RoleStateException;
import com.biblioteca.security_service.exception.UserRoleNotFoundException;
import com.biblioteca.security_service.model.Role;
import com.biblioteca.security_service.model.UserRole;
import com.biblioteca.security_service.repository.RoleRepository;
import com.biblioteca.security_service.repository.UserRoleRepository;
import com.biblioteca.security_service.service.UserRoleService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.security_service.exception.RemoteUserStateException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserClient userClient;

    
    @Override
    public UserRoleResponse asignarRolAUsuario(UserRoleRequest request) {
        log.info("Intentando asignar rol ID {} al usuario ID {}", request.getRoleId(), request.getUserId());

        try {
            UserClientResponse usuario = userClient.buscarUsuarioPorId(request.getUserId());
            log.info("Usuario encontrado en user-service con ID: {}", usuario.getId());

            if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
                log.warn("No se puede asignar rol. Usuario ID {} está inactivo", usuario.getId());
                throw new RemoteUserStateException("No se puede asignar un rol a un usuario inactivo");
            }

        } catch (FeignException.NotFound ex) {
            log.warn("Usuario no encontrado en user-service con ID: {}", request.getUserId());
            throw new RemoteUserNotFoundException("No existe usuario con ID: " + request.getUserId());
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> {
                    log.warn("Rol no encontrado con ID: {}", request.getRoleId());
                    return new RoleNotFoundException("Rol no encontrado con ID: " + request.getRoleId());
                });

        if (!"ACTIVO".equalsIgnoreCase(role.getEstado())) {
            log.warn("No se puede asignar un rol inactivo. Rol ID: {}", role.getId());
            throw new RoleStateException("No se puede asignar un rol inactivo");
        }

        if (userRoleRepository.existsByUserIdAndRoleId(request.getUserId(), request.getRoleId())) {
            log.warn("Asignación duplicada. Usuario ID {} ya tiene rol ID {}", request.getUserId(),
                    request.getRoleId());
            throw new DuplicateUserRoleException("El usuario ya tiene asignado este rol");
        }

        UserRole userRole = UserRole.builder()
                .userId(request.getUserId())
                .roleId(request.getRoleId())
                .estado("ACTIVO")
                .fechaAsignacion(LocalDateTime.now())
                .build();

        UserRole guardado = userRoleRepository.save(userRole);

        log.info("Rol asignado correctamente. Asignación ID: {}", guardado.getId());

        return convertirAResponse(guardado);
    }

    @Override
    public List<UserRoleResponse> listarRolesPorUsuario(Long userId) {
        log.info("Listando roles asignados al usuario ID: {}", userId);

        return userRoleRepository.findByUserId(userId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public boolean validarRolUsuario(Long userId, String roleName) {
        log.info("Validando si usuario ID {} tiene rol {}", userId, roleName);

        Role role = roleRepository.findByNombre(roleName)
                .orElseThrow(() -> {
                    log.warn("Rol no encontrado con nombre: {}", roleName);
                    return new RoleNotFoundException("Rol no encontrado con nombre: " + roleName);
                });

        boolean tieneRol = userRoleRepository.findByUserIdAndRoleId(userId, role.getId())
                .map(userRole -> "ACTIVO".equalsIgnoreCase(userRole.getEstado()))
                .orElse(false);

        log.info("Resultado validación usuario ID {} con rol {}: {}", userId, roleName, tieneRol);

        return tieneRol;
    }

    @Override
    public void desactivarAsignacion(Long id) {
        log.info("Desactivando asignación de rol con ID: {}", id);

        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Asignación de rol no encontrada con ID: {}", id);
                    return new UserRoleNotFoundException("Asignación de rol no encontrada con ID: " + id);
                });

        userRole.setEstado("INACTIVO");
        userRoleRepository.save(userRole);

        log.info("Asignación de rol desactivada correctamente con ID: {}", id);
    }

    private UserRoleResponse convertirAResponse(UserRole userRole) {
        return UserRoleResponse.builder()
                .id(userRole.getId())
                .userId(userRole.getUserId())
                .roleId(userRole.getRoleId())
                .estado(userRole.getEstado())
                .fechaAsignacion(userRole.getFechaAsignacion())
                .build();
    }
}
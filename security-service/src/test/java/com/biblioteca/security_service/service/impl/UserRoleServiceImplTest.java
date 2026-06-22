package com.biblioteca.security_service.service.impl;

import com.biblioteca.security_service.model.Role;
import com.biblioteca.security_service.model.UserRole;
import com.biblioteca.security_service.repository.RoleRepository;
import com.biblioteca.security_service.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserRoleServiceImpl service;

    @Test
    void validarRolUsuario_debeRetornarTrueParaAsignacionActiva() {
        Role role = Role.builder().id(2L).nombre("BIBLIOTECARIO").estado("ACTIVO").build();
        UserRole userRole = UserRole.builder().userId(10L).roleId(2L).estado("ACTIVO").build();
        when(roleRepository.findByNombre("BIBLIOTECARIO")).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserIdAndRoleId(10L, 2L)).thenReturn(Optional.of(userRole));

        assertTrue(service.validarRolUsuario(10L, "BIBLIOTECARIO"));
    }
}

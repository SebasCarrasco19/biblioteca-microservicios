package com.biblioteca.user_service.service.impl;

import com.biblioteca.user_service.dto.UserRequest;
import com.biblioteca.user_service.dto.UserResponse;
import com.biblioteca.user_service.exception.DuplicateEmailException;
import com.biblioteca.user_service.exception.DuplicatePhoneException;
import com.biblioteca.user_service.exception.UserNotFoundException;
import com.biblioteca.user_service.exception.UserStateException;
import com.biblioteca.user_service.model.User;
import com.biblioteca.user_service.repository.UserRepository;
import com.biblioteca.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserResponse crearUsuario(UserRequest request) {
        log.info("Intentando crear usuario con email: {}", request.getEmail());

        if (repository.existsByEmail(request.getEmail())) {
            log.warn("No se pudo crear usuario. Email duplicado: {}", request.getEmail());
            throw new DuplicateEmailException("El email ya está registrado");
        }

        if (repository.existsByTelefono(request.getTelefono())) {
            log.warn("No se pudo crear usuario. Teléfono duplicado: {}", request.getTelefono());
            throw new DuplicatePhoneException("El teléfono ya está registrado");
        }

        User user = User.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .build();

        User guardado = repository.save(user);

        log.info("Usuario creado correctamente con ID: {}", guardado.getId());

        return convertirAResponse(guardado);
    }

    @Override
    public List<UserResponse> listarUsuarios() {
        log.info("Listando todos los usuarios");

        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public UserResponse buscarPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);

        User user = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("Usuario no encontrado con ID: " + id);
                });

        return convertirAResponse(user);
    }

    @Override
    public UserResponse actualizarUsuario(Long id, UserRequest request) {
        log.info("Actualizando usuario con ID: {}", id);

        User user = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se pudo actualizar. Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("Usuario no encontrado con ID: " + id);
                });

        if (repository.existsByEmailAndIdNot(request.getEmail(), id)) {
            log.warn("No se pudo actualizar. Email ya usado por otro usuario: {}", request.getEmail());
            throw new DuplicateEmailException("El email ya está registrado por otro usuario");
        }

        if (repository.existsByTelefonoAndIdNot(request.getTelefono(), id)) {
            log.warn("No se pudo actualizar. Teléfono ya usado por otro usuario: {}", request.getTelefono());
            throw new DuplicatePhoneException("El teléfono ya está registrado por otro usuario");
        }

        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getTelefono());

        User actualizado = repository.save(user);

        log.info("Usuario actualizado correctamente con ID: {}", actualizado.getId());

        return convertirAResponse(actualizado);
    }

    @Override
    public void desactivarUsuario(Long id) {
        log.info("Desactivando usuario con ID: {}", id);

        User user = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se pudo desactivar. Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("Usuario no encontrado con ID: " + id);
                });

        if ("INACTIVO".equalsIgnoreCase(user.getEstado())) {
            log.warn("No se pudo desactivar. El usuario con ID {} ya está inactivo", id);
            throw new UserStateException("El usuario ya está inactivo");
        }

        user.setEstado("INACTIVO");
        repository.save(user);

        log.info("Usuario desactivado correctamente con ID: {}", id);
    }

    @Override
    public UserResponse activarUsuario(Long id) {
        log.info("Activando usuario con ID: {}", id);

        User user = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se pudo activar. Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("Usuario no encontrado con ID: " + id);
                });

        if ("ACTIVO".equalsIgnoreCase(user.getEstado())) {
            log.warn("No se pudo activar. El usuario con ID {} ya está activo", id);
            throw new UserStateException("El usuario ya está activo");
        }

        user.setEstado("ACTIVO");

        User actualizado = repository.save(user);

        log.info("Usuario activado correctamente con ID: {}", id);

        return convertirAResponse(actualizado);
    }

    private UserResponse convertirAResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .estado(user.getEstado())
                .fechaCreacion(user.getFechaCreacion())
                .build();
    }
}
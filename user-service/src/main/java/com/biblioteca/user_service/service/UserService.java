package com.biblioteca.user_service.service;

import com.biblioteca.user_service.dto.UserRequest;
import com.biblioteca.user_service.dto.UserResponse;
import com.biblioteca.user_service.exception.DuplicateEmailException;
import com.biblioteca.user_service.exception.UserNotFoundException;
import com.biblioteca.user_service.model.User;
import com.biblioteca.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse crearUsuario(UserRequest request) {
        log.info("Intentando crear usuario con email: {}", request.getEmail());

        if (repository.existsByEmail(request.getEmail())) {
            log.warn("No se pudo crear usuario. Email duplicado: {}", request.getEmail());
            throw new DuplicateEmailException("El email ya está registrado");
        }

        if (repository.existsByTelefono(request.getTelefono())) {
            log.warn("No se pudo crear usuario. Teléfono duplicado: {}", request.getTelefono());
            throw new DuplicateEmailException("El teléfono ya está registrado");
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

    public List<UserResponse> listarUsuarios() {
        log.info("Listando todos los usuarios");

        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public UserResponse buscarPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);

        User user = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("Usuario no encontrado con ID: " + id);
                });

        return convertirAResponse(user);
    }

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
            throw new DuplicateEmailException("El teléfono ya está registrado por otro usuario");
        }

        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getTelefono());

        User actualizado = repository.save(user);

        log.info("Usuario actualizado correctamente con ID: {}", actualizado.getId());

        return convertirAResponse(actualizado);
    }

    public void desactivarUsuario(Long id) {
        log.info("Desactivando usuario con ID: {}", id);

        User user = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se pudo desactivar. Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("Usuario no encontrado con ID: " + id);
                });

        user.setEstado("INACTIVO");
        repository.save(user);

        log.info("Usuario desactivado correctamente con ID: {}", id);
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

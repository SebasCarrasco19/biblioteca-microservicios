
package com.biblioteca.user_service.service;

import com.biblioteca.user_service.dto.UserRequest;
import com.biblioteca.user_service.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse crearUsuario(UserRequest request);

    List<UserResponse> listarUsuarios();

    UserResponse buscarPorId(Long id);

    UserResponse actualizarUsuario(Long id, UserRequest request);

    void desactivarUsuario(Long id);

    UserResponse activarUsuario(Long id);
}
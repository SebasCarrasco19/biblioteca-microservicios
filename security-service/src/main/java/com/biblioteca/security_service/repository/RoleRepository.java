package com.biblioteca.security_service.repository;

import com.biblioteca.security_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, Long id);

    Optional<Role> findByNombre(String nombre);
}
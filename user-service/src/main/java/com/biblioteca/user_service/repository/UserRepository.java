package com.biblioteca.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.user_service.model.User;

public interface UserRepository  extends JpaRepository<User,Long>{

    boolean existsByEmail(String email);
    
    boolean existsByTelefono(String Telefono);

    Optional<User> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email,Long id);

    boolean existsByTelefonoAndIdNot(String telefono,Long id);

}

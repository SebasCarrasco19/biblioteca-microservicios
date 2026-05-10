package com.biblioteca.security_service.repository;

import com.biblioteca.security_service.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUserId(Long userId);

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);
}

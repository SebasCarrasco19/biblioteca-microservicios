package com.biblioteca.copy_service.repository;

import com.biblioteca.copy_service.model.Copy;
import com.biblioteca.copy_service.model.CopyEstado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    Optional<Copy> findByIdAndEstado(Long id, CopyEstado estado);
    boolean existsByInventoryCodeIgnoreCase(String code);
}

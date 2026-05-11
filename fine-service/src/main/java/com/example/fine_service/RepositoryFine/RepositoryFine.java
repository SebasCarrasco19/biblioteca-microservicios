package com.example.fine_service.RepositoryFine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fine_service.ModelFine.ModelFine;

@Repository
public interface RepositoryFine extends JpaRepository<ModelFine, Long> {

    List<ModelFine> findByUserId(Long userId);

    List<ModelFine> findByPaid(Boolean paid);
}
package com.example.fine_service.RepositoryFine;

import com.example.fine_service.ModelFine.FineStatus;
import com.example.fine_service.ModelFine.ModelFine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryFine extends JpaRepository<ModelFine, Long> {

    List<ModelFine> findByUserId(Long userId);

    List<ModelFine> findByLoanId(Long loanId);

    List<ModelFine> findByPaid(Boolean paid);

    List<ModelFine> findByStatus(FineStatus status);

    boolean existsByLoanIdAndStatusNot(Long loanId, FineStatus status);
}
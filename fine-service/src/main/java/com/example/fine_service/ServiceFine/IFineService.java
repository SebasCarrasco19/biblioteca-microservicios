package com.example.fine_service.ServiceFine;

import com.example.fine_service.DtoFine.DtoFine;
import com.example.fine_service.ModelFine.ModelFine;

import java.util.List;

public interface IFineService {

    List<ModelFine> findAll();

    ModelFine findById(Long id);

    List<ModelFine> findByUserId(Long userId);

    List<ModelFine> findByLoanId(Long loanId);

    List<ModelFine> findPendingFines();

    ModelFine save(DtoFine dtoFine);

    ModelFine payFine(Long id);

    void cancelFine(Long id);
}
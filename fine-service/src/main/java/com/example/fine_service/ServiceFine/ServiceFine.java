package com.example.fine_service.ServiceFine;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fine_service.DtoFine.DtoFine;
import com.example.fine_service.ModelFine.ModelFine;
import com.example.fine_service.RepositoryFine.RepositoryFine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceFine implements IFineService {

    private final RepositoryFine repositoryFine;

    @Override
    public List<ModelFine> findAll() {
        log.info("Obteniendo todas las multas");
        return repositoryFine.findAll();
    }

    @Override
    public ModelFine findById(Long id) {
        log.info("Buscando multa con ID {}", id);

        return repositoryFine.findById(id)
                .orElseThrow(() -> new RuntimeException("Multa no encontrada con ID: " + id));
    }

    @Override
    public List<ModelFine> findByUserId(Long userId) {
        log.info("Buscando multas del usuario {}", userId);
        return repositoryFine.findByUserId(userId);
    }

    @Override
    public ModelFine save(DtoFine dtoFine) {
        log.info("Creando nueva multa para usuario {}", dtoFine.getUserId());

        double amount = dtoFine.getDaysLate() * 1000.0;

        ModelFine fine = ModelFine.builder()
                .userId(dtoFine.getUserId())
                .loanId(dtoFine.getLoanId())
                .amount(amount)
                .reason(dtoFine.getReason())
                .paid(false)
                .createdAt(LocalDateTime.now())
                .build();

        return repositoryFine.save(fine);
    }

    @Override
    public ModelFine payFine(Long id) {
        log.info("Pagando multa con ID {}", id);

        ModelFine fine = findById(id);
        fine.setPaid(true);

        return repositoryFine.save(fine);
    }

    @Override
    public void delete(Long id) {
        log.info("Eliminando multa con ID {}", id);

        ModelFine fine = findById(id);
        repositoryFine.delete(fine);
    }
}
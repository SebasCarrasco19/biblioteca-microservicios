package com.example.fine_service.ServiceFine;

import com.example.fine_service.ClienteFine.LoanClient;
import com.example.fine_service.ClienteFine.NotificationClient;
import com.example.fine_service.ClienteFine.UserClient;
import com.example.fine_service.DtoFine.DtoFine;
import com.example.fine_service.DtoFine.LoanClientResponse;
import com.example.fine_service.DtoFine.NotificationRequest;
import com.example.fine_service.DtoFine.UserClientResponse;
import com.example.fine_service.ExceptionFine.*;
import com.example.fine_service.ModelFine.FineStatus;
import com.example.fine_service.ModelFine.ModelFine;
import com.example.fine_service.RepositoryFine.RepositoryFine;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceFine implements IFineService {

    private static final double DAILY_FINE_AMOUNT = 1000.0;

    private final RepositoryFine repositoryFine;
    private final UserClient userClient;
    private final LoanClient loanClient;
    private final NotificationClient notificationClient;

    @Override
    public List<ModelFine> findAll() {
        log.info("Obteniendo todas las multas");
        return repositoryFine.findAll();
    }

    @Override
    public ModelFine findById(Long id) {
        log.info("Buscando multa con ID: {}", id);

        return repositoryFine.findById(id)
                .orElseThrow(() -> new FineNotFoundException(id));
    }

    @Override
    public List<ModelFine> findByUserId(Long userId) {
        log.info("Buscando multas del usuario ID: {}", userId);

        validateUser(userId);

        return repositoryFine.findByUserId(userId);
    }

    @Override
    public List<ModelFine> findByLoanId(Long loanId) {
        log.info("Buscando multas del préstamo ID: {}", loanId);

        validateLoan(loanId);

        return repositoryFine.findByLoanId(loanId);
    }

    @Override
    public List<ModelFine> findPendingFines() {
        log.info("Buscando multas pendientes");
        return repositoryFine.findByStatus(FineStatus.PENDING);
    }

    @Override
    public ModelFine save(DtoFine dtoFine) {
        log.info("Creando multa para usuario ID: {} y préstamo ID: {}",
                dtoFine.getUserId(), dtoFine.getLoanId());

        validateUser(dtoFine.getUserId());

        LoanClientResponse loan = validateLoan(dtoFine.getLoanId());

        if (loan.getUserId() != null && !loan.getUserId().equals(dtoFine.getUserId())) {
            throw new RemoteLoanStateException("El préstamo no pertenece al usuario indicado");
        }

        if (repositoryFine.existsByLoanIdAndStatusNot(dtoFine.getLoanId(), FineStatus.CANCELLED)) {
            throw new FineStateException("Ya existe una multa activa o pagada para este préstamo");
        }

        double amount = dtoFine.getDaysLate() * DAILY_FINE_AMOUNT;

        ModelFine fine = ModelFine.builder()
                .userId(dtoFine.getUserId())
                .loanId(dtoFine.getLoanId())
                .daysLate(dtoFine.getDaysLate())
                .amount(amount)
                .reason(dtoFine.getReason())
                .paid(false)
                .status(FineStatus.PENDING)
                .build();

        ModelFine savedFine = repositoryFine.save(fine);

        sendNotificationSafely(
                savedFine.getUserId(),
                "Multa registrada",
                "Se registró una multa por " + savedFine.getDaysLate()
                        + " día(s) de atraso. Monto: $" + savedFine.getAmount(),
                "FINE"
        );

        return savedFine;
    }

    @Override
    public ModelFine payFine(Long id) {
        log.info("Pagando multa con ID: {}", id);

        ModelFine fine = findById(id);

        if (FineStatus.CANCELLED.equals(fine.getStatus())) {
            throw new FineStateException("No se puede pagar una multa cancelada");
        }

        if (FineStatus.PAID.equals(fine.getStatus()) || Boolean.TRUE.equals(fine.getPaid())) {
            throw new FineStateException("La multa ya está pagada");
        }

        fine.setPaid(true);
        fine.setStatus(FineStatus.PAID);
        fine.setPaidAt(LocalDateTime.now());

        ModelFine updatedFine = repositoryFine.save(fine);

        sendNotificationSafely(
                updatedFine.getUserId(),
                "Multa pagada",
                "La multa con ID " + updatedFine.getId() + " fue marcada como pagada.",
                "FINE"
        );

        return updatedFine;
    }

    @Override
    public void cancelFine(Long id) {
        log.info("Cancelando multa con ID: {}", id);

        ModelFine fine = findById(id);

        if (FineStatus.CANCELLED.equals(fine.getStatus())) {
            throw new FineStateException("La multa ya está cancelada");
        }

        if (FineStatus.PAID.equals(fine.getStatus())) {
            throw new FineStateException("No se puede cancelar una multa pagada");
        }

        fine.setStatus(FineStatus.CANCELLED);
        fine.setCancelledAt(LocalDateTime.now());

        repositoryFine.save(fine);

        sendNotificationSafely(
                fine.getUserId(),
                "Multa cancelada",
                "La multa con ID " + fine.getId() + " fue cancelada.",
                "FINE"
        );
    }

    private void validateUser(Long userId) {
        try {
            UserClientResponse user = userClient.findById(userId);

            if (user == null) {
                throw new RemoteUserNotFoundException(userId);
            }

            if (user.getEstado() == null || !"ACTIVO".equalsIgnoreCase(user.getEstado())) {
                throw new RemoteUserStateException("El usuario no está activo");
            }

        } catch (FeignException.NotFound ex) {
            throw new RemoteUserNotFoundException(userId);

        } catch (RemoteUserNotFoundException | RemoteUserStateException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new RemoteServiceException("Error al comunicarse con user-service");
        }
    }

    private LoanClientResponse validateLoan(Long loanId) {
        try {
            LoanClientResponse loan = loanClient.findById(loanId);

            if (loan == null) {
                throw new RemoteLoanNotFoundException(loanId);
            }

            if (loan.getStatus() == null) {
                throw new RemoteLoanStateException("El préstamo no tiene estado válido");
            }

            if ("CANCELADO".equalsIgnoreCase(loan.getStatus())) {
                throw new RemoteLoanStateException("No se puede crear multa para un préstamo cancelado");
            }

            return loan;

        } catch (FeignException.NotFound ex) {
            throw new RemoteLoanNotFoundException(loanId);

        } catch (RemoteLoanNotFoundException | RemoteLoanStateException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new RemoteServiceException("Error al comunicarse con loan-service");
        }
    }

    private void sendNotificationSafely(Long userId, String title, String message, String type) {
        try {
            NotificationRequest request = new NotificationRequest(userId, title, message, type);
            notificationClient.createNotification(request);

        } catch (Exception ex) {
            log.warn("No se pudo crear la notificación en notification-service. userId={}, title={}",
                    userId, title);
        }
    }
}
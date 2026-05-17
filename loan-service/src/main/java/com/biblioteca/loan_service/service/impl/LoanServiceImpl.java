package com.biblioteca.loan_service.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.biblioteca.loan_service.client.CopyClient;
import com.biblioteca.loan_service.client.UserClient;
import com.biblioteca.loan_service.dto.CopyClientResponse;
import com.biblioteca.loan_service.dto.LoanRequest;
import com.biblioteca.loan_service.dto.LoanResponse;
import com.biblioteca.loan_service.dto.UserClientResponse;
import com.biblioteca.loan_service.exception.LoanNotFoundException;
import com.biblioteca.loan_service.exception.LoanStateException;
import com.biblioteca.loan_service.exception.RemoteCopyNotFoundException;
import com.biblioteca.loan_service.exception.RemoteCopyStateException;
import com.biblioteca.loan_service.exception.RemoteServiceException;
import com.biblioteca.loan_service.exception.RemoteUserNotFoundException;
import com.biblioteca.loan_service.exception.RemoteUserStateException;
import com.biblioteca.loan_service.model.Loan;
import com.biblioteca.loan_service.model.LoanStatus;
import com.biblioteca.loan_service.repository.LoanRepository;
import com.biblioteca.loan_service.service.LoanService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository repository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CopyClient copyClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public LoanResponse createLoan(LoanRequest request, Long userId) {
        log.info("Intentando crear préstamo para usuario ID: {} y ejemplar ID: {}",
                request.getUserId(), request.getCopyId());

        validateUser(request.getUserId());
        validateCopyAvailable(request.getCopyId());

        if (repository.existsByCopyIdAndStatus(request.getCopyId(), LoanStatus.ACTIVO)) {
            throw new LoanStateException("El ejemplar ya tiene un préstamo activo");
        }

        markCopyAsLoaned(request.getCopyId(), userId);

        Loan loan = new Loan();
        loan.setUserId(request.getUserId());
        loan.setCopyId(request.getCopyId());
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(request.getDueDate());
        loan.setStatus(LoanStatus.ACTIVO);

        Loan savedLoan = repository.save(loan);

        log.info("Préstamo creado correctamente con ID: {}", savedLoan.getId());

        return convertToResponse(savedLoan);
    }

    @Override
    public List<LoanResponse> getLoans() {
        log.info("Listando préstamos");

        return repository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public LoanResponse getLoanById(Long id) {
        log.info("Buscando préstamo con ID: {}", id);

        Loan loan = findLoanById(id);

        return convertToResponse(loan);
    }

    @Override
    public List<LoanResponse> getLoansByUser(Long userId) {
        log.info("Listando préstamos del usuario ID: {}", userId);

        validateUser(userId);

        return repository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public LoanResponse returnLoan(Long id, Long userId) {
        log.info("Intentando devolver préstamo con ID: {}", id);

        Loan loan = findLoanById(id);

        if (LoanStatus.DEVUELTO.equals(loan.getStatus())) {
            throw new LoanStateException("El préstamo ya fue devuelto");
        }

        if (LoanStatus.CANCELADO.equals(loan.getStatus())) {
            throw new LoanStateException("No se puede devolver un préstamo cancelado");
        }

        markCopyAsAvailable(loan.getCopyId(), userId);

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.DEVUELTO);

        Loan updatedLoan = repository.save(loan);

        log.info("Préstamo devuelto correctamente con ID: {}", id);

        return convertToResponse(updatedLoan);
    }

    @Override
    public LoanResponse cancelLoan(Long id, Long userId) {
        log.info("Intentando cancelar préstamo con ID: {}", id);

        Loan loan = findLoanById(id);

        if (LoanStatus.DEVUELTO.equals(loan.getStatus())) {
            throw new LoanStateException("No se puede cancelar un préstamo devuelto");
        }

        if (LoanStatus.CANCELADO.equals(loan.getStatus())) {
            throw new LoanStateException("El préstamo ya está cancelado");
        }

        markCopyAsAvailable(loan.getCopyId(), userId);

        loan.setStatus(LoanStatus.CANCELADO);

        Loan updatedLoan = repository.save(loan);

        log.info("Préstamo cancelado correctamente con ID: {}", id);

        return convertToResponse(updatedLoan);
    }

    @Override
    public LoanResponse markLoanAsOverdue(Long id) {
        log.info("Intentando marcar préstamo como atrasado con ID: {}", id);

        Loan loan = findLoanById(id);

        if (!LoanStatus.ACTIVO.equals(loan.getStatus())) {
            throw new LoanStateException("Solo se puede marcar como atrasado un préstamo activo");
        }

        if (!LocalDate.now().isAfter(loan.getDueDate())) {
            throw new LoanStateException("El préstamo todavía no está atrasado");
        }

        loan.setStatus(LoanStatus.ATRASADO);

        Loan updatedLoan = repository.save(loan);

        log.info("Préstamo marcado como atrasado correctamente con ID: {}", id);

        return convertToResponse(updatedLoan);
    }

    private Loan findLoanById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    private void validateUser(Long userId) {
        log.info("Validando usuario con ID: {}", userId);

        try {
            UserClientResponse user = userClient.findById(userId);

            if (user == null) {
                throw new RemoteUserNotFoundException(userId);
            }

            if (user.getEstado() == null || !"ACTIVO".equalsIgnoreCase(user.getEstado())) {
                throw new RemoteUserStateException("El usuario está inactivo");
            }

        } catch (FeignException.NotFound ex) {
            throw new RemoteUserNotFoundException(userId);

        } catch (RemoteUserNotFoundException | RemoteUserStateException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new RemoteServiceException("Error al comunicarse con user-service");
        }
    }

    private void validateCopyAvailable(Long copyId) {
        log.info("Validando ejemplar con ID: {}", copyId);

        try {
            CopyClientResponse copy = copyClient.findById(copyId);

            if (copy == null) {
                throw new RemoteCopyNotFoundException(copyId);
            }

            if (copy.getStatus() == null || !"DISPONIBLE".equalsIgnoreCase(copy.getStatus())) {
                throw new RemoteCopyStateException("El ejemplar no está disponible para préstamo");
            }

        } catch (FeignException.NotFound ex) {
            throw new RemoteCopyNotFoundException(copyId);

        } catch (RemoteCopyNotFoundException | RemoteCopyStateException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new RemoteServiceException("Error al comunicarse con copy-service");
        }
    }

private void markCopyAsLoaned(Long copyId, Long userId) {
    try {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", String.valueOf(userId));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                "http://copy-service/api/copies/{id}/borrow",
                HttpMethod.PATCH,
                entity,
                Void.class,
                copyId
        );

    } catch (RestClientResponseException ex) {
        log.error("Error HTTP al marcar ejemplar como PRESTADO. copyId={}, userId={}, status={}, body={}",
                copyId, userId, ex.getStatusCode(), ex.getResponseBodyAsString(), ex);

        throw new RemoteServiceException(
                "Error al actualizar el estado del ejemplar en copy-service. Status: "
                        + ex.getStatusCode().value()
                        + " - Respuesta: "
                        + ex.getResponseBodyAsString()
        );

    } catch (RestClientException ex) {
        log.error("Error de conexión al marcar ejemplar como PRESTADO. copyId={}, userId={}",
                copyId, userId, ex);

        throw new RemoteServiceException(
                "Error al actualizar el estado del ejemplar en copy-service. Detalle: "
                        + ex.getMessage()
        );
    }
}

private void markCopyAsAvailable(Long copyId, Long userId) {
    try {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", String.valueOf(userId));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                "http://copy-service/api/copies/{id}/return",
                HttpMethod.PATCH,
                entity,
                Void.class,
                copyId
        );

    } catch (RestClientResponseException ex) {
        log.error("Error HTTP al marcar ejemplar como DISPONIBLE. copyId={}, userId={}, status={}, body={}",
                copyId, userId, ex.getStatusCode(), ex.getResponseBodyAsString(), ex);

        throw new RemoteServiceException(
                "Error al actualizar el estado del ejemplar en copy-service. Status: "
                        + ex.getStatusCode().value()
                        + " - Respuesta: "
                        + ex.getResponseBodyAsString()
        );

    } catch (RestClientException ex) {
        log.error("Error de conexión al marcar ejemplar como DISPONIBLE. copyId={}, userId={}",
                copyId, userId, ex);

        throw new RemoteServiceException(
                "Error al actualizar el estado del ejemplar en copy-service. Detalle: "
                        + ex.getMessage()
        );
    }
}
    private LoanResponse convertToResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .userId(loan.getUserId())
                .copyId(loan.getCopyId())
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .status(loan.getStatus().name())
                .createdAt(loan.getCreatedAt())
                .updatedAt(loan.getUpdatedAt())
                .build();
    }
}
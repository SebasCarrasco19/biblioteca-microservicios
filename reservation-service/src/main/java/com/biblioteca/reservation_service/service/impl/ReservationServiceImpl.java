package com.biblioteca.reservation_service.service.impl;

import com.biblioteca.reservation_service.client.CopyClient;
import com.biblioteca.reservation_service.client.UserClient;
import com.biblioteca.reservation_service.dto.ReservationRequest;
import com.biblioteca.reservation_service.dto.ReservationResponse;
import com.biblioteca.reservation_service.exception.CopyNotAvailableException;
import com.biblioteca.reservation_service.exception.RemoteServiceException;
import com.biblioteca.reservation_service.exception.ReservationNotFoundException;
import com.biblioteca.reservation_service.exception.UserNotFoundException;
import com.biblioteca.reservation_service.model.Reservation;
import com.biblioteca.reservation_service.model.ReservationStatus;
import com.biblioteca.reservation_service.repository.ReservationRepository;
import feign.FeignException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements com.biblioteca.reservation_service.service.ReservationService {

    private final ReservationRepository repository;
    private final CopyClient copyClient;
    private final UserClient userClient;

    @Override
    public ReservationResponse crearReserva(ReservationRequest request) {
        validateUser(request.getUserId());
        try {
            copyClient.reserve(request.getCopyId());
        } catch (FeignException.Conflict ex) {
            throw new CopyNotAvailableException(request.getCopyId());
        } catch (FeignException.NotFound ex) {
            throw new CopyNotAvailableException(request.getCopyId());
        } catch (FeignException ex) {
            throw new RemoteServiceException("No fue posible reservar la copia en copy-service");
        }

        Reservation reservation = new Reservation();
        reservation.setUserId(request.getUserId());
        reservation.setCopyId(request.getCopyId());
        reservation.setStatus(ReservationStatus.ACTIVA);
        reservation.setActive(true);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setExpirationDate(resolveExpirationDate(request.getExpirationDate(), reservation.getReservationDate()));

        Reservation saved = repository.save(reservation);
        log.info("Reserva creada id={} userId={} copyId={}", saved.getId(), saved.getUserId(), saved.getCopyId());
        return toResponse(saved);
    }

    @Override
    public List<ReservationResponse> listarReservas() {
        return repository.findAll().stream().filter(Reservation::getActive).map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> listarPorUsuario(Long userId) {
        return repository.findByUserIdAndActiveTrue(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ReservationResponse buscarPorId(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id)));
    }

    @Override
    public void cancelarReserva(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        try {
            copyClient.release(reservation.getCopyId());
        } catch (FeignException ex) {
            throw new RemoteServiceException("No fue posible liberar la copia en copy-service");
        }
        reservation.setStatus(ReservationStatus.CANCELADA);
        reservation.setActive(false);
        repository.save(reservation);
    }

    @Override
    public ReservationResponse activarReserva(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        try {
            copyClient.reserve(reservation.getCopyId());
        } catch (FeignException.Conflict ex) {
            throw new CopyNotAvailableException(reservation.getCopyId());
        } catch (FeignException.NotFound ex) {
            throw new CopyNotAvailableException(reservation.getCopyId());
        } catch (FeignException ex) {
            throw new RemoteServiceException("No fue posible reservar la copia en copy-service");
        }
        reservation.setStatus(ReservationStatus.ACTIVA);
        reservation.setActive(true);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setExpirationDate(reservation.getReservationDate().plusDays(1));
        return toResponse(repository.save(reservation));
    }

    @Override
    public void expirarReserva(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        try {
            copyClient.release(reservation.getCopyId());
        } catch (FeignException ex) {
            throw new RemoteServiceException("No fue posible liberar la copia en copy-service");
        }
        reservation.setStatus(ReservationStatus.EXPIRADA);
        reservation.setActive(false);
        repository.save(reservation);
    }

    private void validateUser(Long userId) {
        try {
            Object response = userClient.findById(userId);
            if (response == null) throw new UserNotFoundException(userId);
        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException(userId);
        } catch (FeignException ex) {
            throw new RemoteServiceException("No fue posible validar usuario en user-service");
        }
    }

    private LocalDateTime resolveExpirationDate(LocalDateTime requestedExpirationDate, LocalDateTime reservationDate) {
        if (requestedExpirationDate == null) return reservationDate.plusDays(1);
        if (!requestedExpirationDate.isAfter(reservationDate)) {
            throw new IllegalArgumentException("La fecha de expiracion debe ser posterior a la fecha de reserva");
        }
        return requestedExpirationDate;
    }

    private ReservationResponse toResponse(Reservation r) {
        return ReservationResponse.builder()
                .id(r.getId())
                .userId(r.getUserId())
                .copyId(r.getCopyId())
                .status(r.getStatus().name())
                .active(r.getActive())
                .reservationDate(r.getReservationDate())
                .expirationDate(r.getExpirationDate())
                .build();
    }
}

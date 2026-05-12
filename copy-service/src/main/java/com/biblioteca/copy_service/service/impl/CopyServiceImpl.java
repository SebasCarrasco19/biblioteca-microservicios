package com.biblioteca.copy_service.service.impl;

import com.biblioteca.copy_service.client.BookClient;
import com.biblioteca.copy_service.dto.BookClientResponse;
import com.biblioteca.copy_service.dto.CopyRequest;
import com.biblioteca.copy_service.dto.CopyResponse;
import com.biblioteca.copy_service.exception.BookNotFoundException;
import com.biblioteca.copy_service.exception.CopyNotFoundException;
import com.biblioteca.copy_service.exception.CopyStateException;
import com.biblioteca.copy_service.exception.DuplicateInventoryCodeException;
import com.biblioteca.copy_service.exception.RemoteServiceException;
import com.biblioteca.copy_service.model.Copy;
import com.biblioteca.copy_service.model.CopyEstado;
import com.biblioteca.copy_service.model.CopyStatus;
import com.biblioteca.copy_service.repository.CopyRepository;
import com.biblioteca.copy_service.service.CopyService;
import feign.FeignException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CopyServiceImpl implements CopyService {

    private final CopyRepository repository;
    private final BookClient bookClient;

    @Override
    public CopyResponse createCopy(CopyRequest request) {
        validateBook(request.getBookId());
        String code = normalizeText(request.getInventoryCode());
        if (repository.existsByInventoryCodeIgnoreCase(code)) {
            log.warn("Codigo de inventario duplicado: {}", code);
            throw new DuplicateInventoryCodeException(code);
        }

        Copy copy = new Copy();
        copy.setBookId(request.getBookId());
        copy.setInventoryCode(code);
        copy.setLocation(normalizeText(request.getLocation()));
        copy.setStatus(CopyStatus.DISPONIBLE);
        copy.setEstado(CopyEstado.ACTIVO);

        Copy saved = repository.save(copy);
        log.info("Copia creada id={} bookId={} inventoryCode={}", saved.getId(), saved.getBookId(), saved.getInventoryCode());
        return toResponse(saved);
    }

    @Override
    public List<CopyResponse> getCopies() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CopyResponse getCopyById(Long id) {
        return toResponse(findCopy(id));
    }

    @Override
    public CopyResponse updateCopy(Long id, CopyRequest request) {
        Copy copy = findCopy(id);
        validateBook(request.getBookId());
        String code = normalizeText(request.getInventoryCode());
        if (!copy.getInventoryCode().equalsIgnoreCase(code) && repository.existsByInventoryCodeIgnoreCase(code)) {
            log.warn("Intento de actualizar a codigo duplicado: {}", code);
            throw new DuplicateInventoryCodeException(code);
        }

        copy.setBookId(request.getBookId());
        copy.setInventoryCode(code);
        copy.setLocation(normalizeText(request.getLocation()));
        Copy updated = repository.save(copy);
        log.info("Copia actualizada id={}", updated.getId());
        return toResponse(updated);
    }

    @Override
    public void deactivateCopy(Long id) {
        Copy copy = findCopy(id);
        if (CopyEstado.INACTIVO.equals(copy.getEstado())) {
            throw new CopyStateException("La copia ya esta INACTIVA");
        }
        copy.setEstado(CopyEstado.INACTIVO);
        repository.save(copy);
        log.info("Copia desactivada id={}", id);
    }

    @Override
    public CopyResponse activateCopy(Long id) {
        Copy copy = findCopy(id);
        if (CopyEstado.ACTIVO.equals(copy.getEstado())) {
            throw new CopyStateException("La copia ya esta ACTIVA");
        }
        copy.setEstado(CopyEstado.ACTIVO);
        Copy activated = repository.save(copy);
        log.info("Copia activada id={}", id);
        return toResponse(activated);
    }

    @Override
    public boolean isAvailable(Long id) {
        Copy copy = findCopy(id);
        return CopyEstado.ACTIVO.equals(copy.getEstado()) && CopyStatus.DISPONIBLE.equals(copy.getStatus());
    }

    @Override
    public CopyResponse reserveCopy(Long id) {
        Copy copy = findCopy(id);
        ensureActive(copy);
        if (!CopyStatus.DISPONIBLE.equals(copy.getStatus())) {
            throw new CopyStateException("Solo se puede reservar una copia DISPONIBLE");
        }
        copy.setStatus(CopyStatus.RESERVADO);
        Copy reserved = repository.save(copy);
        log.info("Copia reservada id={}", id);
        return toResponse(reserved);
    }

    @Override
    public CopyResponse releaseCopy(Long id) {
        Copy copy = findCopy(id);
        ensureActive(copy);
        if (!CopyStatus.RESERVADO.equals(copy.getStatus())) {
            throw new CopyStateException("Solo se puede liberar una copia RESERVADO");
        }
        copy.setStatus(CopyStatus.DISPONIBLE);
        Copy released = repository.save(copy);
        log.info("Copia liberada id={}", id);
        return toResponse(released);
    }

    @Override
    public CopyResponse borrowCopy(Long id) {
        Copy copy = findCopy(id);
        ensureActive(copy);
        if (!CopyStatus.DISPONIBLE.equals(copy.getStatus())) {
            throw new CopyStateException("Solo se puede prestar una copia DISPONIBLE");
        }
        copy.setStatus(CopyStatus.PRESTADO);
        Copy borrowed = repository.save(copy);
        log.info("Copia prestada id={}", id);
        return toResponse(borrowed);
    }

    @Override
    public CopyResponse returnCopy(Long id) {
        Copy copy = findCopy(id);
        ensureActive(copy);
        if (!CopyStatus.PRESTADO.equals(copy.getStatus())) {
            throw new CopyStateException("Solo se puede devolver una copia PRESTADO");
        }
        copy.setStatus(CopyStatus.DISPONIBLE);
        Copy returned = repository.save(copy);
        log.info("Copia devuelta id={}", id);
        return toResponse(returned);
    }

    private Copy findCopy(Long id) {
        return repository.findById(id).orElseThrow(() -> new CopyNotFoundException(id));
    }

    private void ensureActive(Copy copy) {
        if (CopyEstado.INACTIVO.equals(copy.getEstado())) {
            throw new CopyStateException("No se puede operar sobre una copia INACTIVA");
        }
    }

    private void validateBook(Long bookId) {
        try {
            BookClientResponse book = bookClient.getBookById(bookId);
            if (book == null || book.getId() == null || !isActiveBook(book)) {
                throw new BookNotFoundException(bookId);
            }
        } catch (FeignException.NotFound ex) {
            throw new BookNotFoundException(bookId);
        } catch (FeignException ex) {
            throw new RemoteServiceException("No fue posible validar libro en book-service");
        }
    }

    private boolean isActiveBook(BookClientResponse book) {
        String state = book.getEstado() != null ? book.getEstado() : book.getStatus();
        return "ACTIVO".equalsIgnoreCase(state);
    }

    private CopyResponse toResponse(Copy copy) {
        return CopyResponse.builder()
                .id(copy.getId())
                .bookId(copy.getBookId())
                .inventoryCode(copy.getInventoryCode())
                .location(copy.getLocation())
                .status(copy.getStatus().name())
                .estado(copy.getEstado().name())
                .fechaRegistro(copy.getFechaRegistro())
                .build();
    }

    private String normalizeText(String value) {
        return value == null ? null : value.trim();
    }
}

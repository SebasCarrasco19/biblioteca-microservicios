package com.biblioteca.copy_service.service;

import com.biblioteca.copy_service.dto.CopyRequest;
import com.biblioteca.copy_service.dto.CopyResponse;
import java.util.List;

public interface CopyService {
    CopyResponse createCopy(CopyRequest request);
    List<CopyResponse> getCopies();
    CopyResponse getCopyById(Long id);
    CopyResponse updateCopy(Long id, CopyRequest request);
    void deactivateCopy(Long id);
    CopyResponse activateCopy(Long id);
    boolean isAvailable(Long id);
    CopyResponse reserveCopy(Long id);
    CopyResponse releaseCopy(Long id);
    CopyResponse borrowCopy(Long id);
    CopyResponse returnCopy(Long id);
}

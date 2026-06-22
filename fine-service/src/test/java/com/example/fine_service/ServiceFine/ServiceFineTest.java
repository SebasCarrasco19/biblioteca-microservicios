package com.example.fine_service.ServiceFine;

import com.example.fine_service.ClienteFine.LoanClient;
import com.example.fine_service.ClienteFine.NotificationClient;
import com.example.fine_service.ClienteFine.UserClient;
import com.example.fine_service.ModelFine.FineStatus;
import com.example.fine_service.ModelFine.ModelFine;
import com.example.fine_service.RepositoryFine.RepositoryFine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceFineTest {
    @Mock private RepositoryFine repository;
    @Mock private UserClient userClient;
    @Mock private LoanClient loanClient;
    @Mock private NotificationClient notificationClient;

    @Test
    void findById_debeRetornarMulta() {
        ServiceFine service = new ServiceFine(repository, userClient, loanClient, notificationClient);
        ModelFine fine = ModelFine.builder().id(1L).userId(2L).loanId(3L).daysLate(2)
                .amount(2000.0).reason("Atraso").paid(false).status(FineStatus.PENDING).build();
        when(repository.findById(1L)).thenReturn(Optional.of(fine));
        assertEquals(2000.0, service.findById(1L).getAmount());
    }
}

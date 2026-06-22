package com.biblioteca.reservation_service.controller;

import com.biblioteca.reservation_service.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    @Mock private ReservationService service;

    @Test
    void listReservations_debeRetornar200() throws Exception {
        when(service.listarReservas()).thenReturn(List.of());
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ReservationController(service)).build();
        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

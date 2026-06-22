package com.biblioteca.loan_service.controller;

import com.biblioteca.loan_service.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoanControllerTest {
    @Mock private LoanService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LoanController controller = new LoanController();
        ReflectionTestUtils.setField(controller, "service", service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getLoans_debeRetornar200() throws Exception {
        when(service.getLoans()).thenReturn(List.of());
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

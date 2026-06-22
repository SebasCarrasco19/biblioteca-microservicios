package com.example.fine_service.ControllerFine;

import com.example.fine_service.ServiceFine.IFineService;
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
class ControllerFineTest {
    @Mock private IFineService service;

    @Test
    void getAllFines_debeRetornar200() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ControllerFine(service)).build();
        mockMvc.perform(get("/api/fines"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

package com.biblioteca.security_service.controller;

import com.biblioteca.security_service.service.RoleService;
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
class RoleControllerTest {

    @Mock
    private RoleService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RoleController controller = new RoleController();
        ReflectionTestUtils.setField(controller, "service", service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listarRoles_debeRetornar200() throws Exception {
        when(service.listarRoles()).thenReturn(List.of());
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

package com.example.category_service.ControllerCategory;

import com.example.category_service.ServiceCategory.ICategoryService;
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
class ControllerCategoryTest {
    @Mock
    private ICategoryService service;

    @Test
    void getAllCategories_debeRetornar200() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ControllerCategory(service)).build();
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

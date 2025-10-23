package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.domain.enums.ProductStatus;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProducts() throws Exception{
        mockMvc.perform(get("/products")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)));
    }

    @Test
    void getAProduct() throws Exception{
        mockMvc.perform(get("/products/1")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop Pro X15"));
    }

    @Test
    void postAProduct() throws Exception{
        ProductInputDto productInputDto = new ProductInputDto("MacBook", 1499.99F, ProductStatus.ARCHIVED);

        mockMvc.perform(post("/products")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("MacBook"));
    }

    @Test
    void patchAProduct() throws Exception{
        ProductInputDto productInputDto = new ProductInputDto("MacBook Air", null, null);

        mockMvc.perform(patch("/products/1")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productInputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MacBook Air"));
    }

    @Test
    void deleteAProduct() throws Exception{
        mockMvc.perform(delete("/products/4")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.domain.enums.OrderStatus;
import com.example.angelneria_final_project.infraestructure.dto.input.OrderInputDto;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductAmountInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllOrders() throws Exception{
        mockMvc.perform(get("/orders")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(14)));
    }

    @Test
    void getAnOrder() throws Exception{
        mockMvc.perform(get("/orders/1")
                        .with(user("ana.garcia@example.com").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DELIVERED"));
    }

    @Test
    void postAnOrder() throws Exception{
        ProductAmountInputDto productAmountInputDto = new ProductAmountInputDto(1L,2);
        List<ProductAmountInputDto> products= List.of(productAmountInputDto);
        OrderInputDto orderInputDto = new OrderInputDto(OrderStatus.PENDING,1L, products);

        mockMvc.perform(post("/orders")
                        .with(user("ana.garcia@example.com").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.products[0].product.name").value("Laptop Pro X15"));
    }

    @Test
    void patchAnOrder() throws Exception{
        ProductAmountInputDto productAmountInputDto = new ProductAmountInputDto(3L,2);
        List<ProductAmountInputDto> products= List.of(productAmountInputDto);
        OrderInputDto orderInputDto = new OrderInputDto(OrderStatus.PENDING,2L, products);

        mockMvc.perform(patch("/orders/1")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderInputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].product.name").value("Wireless Headphones"))
                .andExpect(jsonPath("$.user.email").value("john.smith@example.com"));

    }
}
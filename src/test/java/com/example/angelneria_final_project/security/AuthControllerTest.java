package com.example.angelneria_final_project.security;

import com.example.angelneria_final_project.domain.enums.Roles;
import com.example.angelneria_final_project.infraestructure.dto.input.LoginDto;
import com.example.angelneria_final_project.infraestructure.dto.input.RegistryInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPerson() throws Exception{
        RegistryInputDto registryInputDto = new RegistryInputDto("Ángel Neria", "angelneria@nter.com", "12345678", "ES", Roles.USER);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registryInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Ángel Neria"));
    }

    @Test
    void login() throws Exception{
        LoginDto loginDto = new LoginDto("ana.garcia@example.com", "user1234");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk());
    }
}
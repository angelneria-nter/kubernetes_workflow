package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.domain.enums.Roles;
import com.example.angelneria_final_project.infraestructure.dto.input.UserInputDto;
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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    void getAnUser() throws Exception {
        mockMvc.perform(get("/users/1")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ana.garcia@example.com"));
    }

    @Test
    void postAnUser() throws Exception{
        UserInputDto userInputDto = new UserInputDto("Ángel Neria", "angelneria@nter.com", "12345678", true, "ES", Roles.USER);

        mockMvc.perform(post("/users")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Ángel Neria"));
    }

    @Test
    void patchAnUser() throws Exception{
        UserInputDto userInputDto = new UserInputDto("Ángel Neria", null, null, null, null, Roles.ADMIN);

        mockMvc.perform(patch("/users/1")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Ángel Neria"));
    }

    @Test
    void deleteAnUser() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void patchAnUserCountry() throws Exception {
        mockMvc.perform(patch("/users/1/country")
                        .header("countryId", "US")
                        .with(user("ana.garcia@example.com").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country.name").value("United States"));
    }
}
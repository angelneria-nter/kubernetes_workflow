package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.infraestructure.dto.input.CountryInputDto;
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
class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCountries() throws Exception {
        mockMvc.perform(get("/countries")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)));
    }

    @Test
    void getACountry() throws Exception {
        mockMvc.perform(get("/countries/ES")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spain"));
    }

    @Test
    void postACountry() throws Exception {

        CountryInputDto countryInputDto = new CountryInputDto("VEN", "Venezuela");

        mockMvc.perform(post("/countries")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Venezuela"));
    }

    @Test
    void patchACountry() throws Exception {
        CountryInputDto countryInputDto = new CountryInputDto(null, "España");

        mockMvc.perform(patch("/countries/ES")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryInputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("España"));
    }

    @Test
    void deleteACountry() throws Exception{
        mockMvc.perform(delete("/countries/ES")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
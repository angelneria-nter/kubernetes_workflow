package com.example.angelneria_final_project.domain.repositories;

import com.example.angelneria_final_project.domain.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}

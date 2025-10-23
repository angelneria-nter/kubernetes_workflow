package com.example.angelneria_final_project.application;

import com.example.angelneria_final_project.infraestructure.dto.input.CountryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.CountrySimpleOutputDto;

import java.util.List;

public interface CountryService {

    List<CountrySimpleOutputDto> getAllCountries();
    CountrySimpleOutputDto getACountry(String id);
    CountrySimpleOutputDto createACountry(CountryInputDto countryInputDto);
    CountrySimpleOutputDto updateACountry(CountryInputDto countryInputDto, String id);
    void deleteACountry(String id);
}

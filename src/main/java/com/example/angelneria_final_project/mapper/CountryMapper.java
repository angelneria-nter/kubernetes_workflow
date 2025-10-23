package com.example.angelneria_final_project.mapper;

import com.example.angelneria_final_project.domain.entities.Country;
import com.example.angelneria_final_project.infraestructure.dto.input.CountryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.CountrySimpleOutputDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    List<CountrySimpleOutputDto> countryListToCountryDtoList(List<Country> countryList);

    CountrySimpleOutputDto countryToCountryDto(Country country);

    Country countryDtoToCountry(CountryInputDto countryInputDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCountryFromDto(CountryInputDto countryInputDto, @MappingTarget Country country);
    
}

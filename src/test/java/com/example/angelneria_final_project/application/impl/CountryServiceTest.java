package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.domain.entities.Country;
import com.example.angelneria_final_project.domain.entities.User;
import com.example.angelneria_final_project.domain.repositories.CountryRepository;
import com.example.angelneria_final_project.domain.repositories.UserRepository;
import com.example.angelneria_final_project.infraestructure.dto.input.CountryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.CountrySimpleOutputDto;
import com.example.angelneria_final_project.mapper.CountryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryMapper countryMapper;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CountryServiceImpl countryService;

    @Test
    void getAllCountries() {

        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        List<Country> countries = List.of(country1);

        CountrySimpleOutputDto outputCounty= new CountrySimpleOutputDto("CH", "China");
        List<CountrySimpleOutputDto> outputsCountries = List.of(outputCounty);

        when(countryRepository.findAll()).thenReturn(countries);
        when(countryMapper.countryListToCountryDtoList(countries)).thenReturn(outputsCountries);

        List<CountrySimpleOutputDto> result = countryService.getAllCountries();
        assertEquals(1,result.size());
        assertEquals("CH", result.get(0).getCode());
        verify(countryRepository, times(1)).findAll();
        verify(countryMapper, times(1)).countryListToCountryDtoList(countries);

    }

    @Test
    void getACountry() {
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        CountrySimpleOutputDto outputCounty= new CountrySimpleOutputDto("CH", "China");

        when(countryRepository.findById("CH")).thenReturn(Optional.ofNullable(country1));
        when(countryMapper.countryToCountryDto(country1)).thenReturn(outputCounty);

        CountrySimpleOutputDto result = countryService.getACountry("CH");

        assertEquals("CH", result.getCode());
        assertEquals("China", result.getName());
        verify(countryRepository, times(1)).findById("CH");
        verify(countryMapper, times(1)).countryToCountryDto(country1);


    }

    @Test
    void createACountry() {
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        CountryInputDto countryInputDto = new CountryInputDto("CH", "China");
        CountrySimpleOutputDto outputCounty= new CountrySimpleOutputDto("CH", "China");

        when(countryRepository.findById("CH")).thenReturn(Optional.empty());
        when(countryMapper.countryDtoToCountry(countryInputDto)).thenReturn(country1);
        when(countryRepository.save(country1)).thenReturn(country1);
        when(countryMapper.countryToCountryDto(country1)).thenReturn(outputCounty);

        CountrySimpleOutputDto result = countryService.createACountry(countryInputDto);

        assertEquals("CH",result.getCode());
        verify(countryRepository, times(1)).findById("CH");
        verify(countryMapper, times(1)).countryDtoToCountry(countryInputDto);
        verify(countryRepository, times(1)).save(country1);
        verify(countryMapper, times(1)).countryToCountryDto(country1);

    }

    @Test
    void updateACountry() {
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        CountryInputDto countryInputDto = new CountryInputDto("CH", "China");
        CountrySimpleOutputDto outputCounty= new CountrySimpleOutputDto("CH", "CHINA");

        when(countryRepository.findById("CH")).thenReturn(Optional.ofNullable(country1));
        when(countryRepository.save(country1)).thenReturn(country1);
        when(countryMapper.countryToCountryDto(country1)).thenReturn(outputCounty);

        CountrySimpleOutputDto result = countryService.updateACountry(countryInputDto, "CH");

        assertEquals("CHINA", result.getName());
        verify(countryRepository, times(1)).findById("CH");
        verify(countryRepository, times(1)).save(country1);
        verify(countryMapper, times(1)).countryToCountryDto(country1);

    }

    @Test
    void deleteACountry() {
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        CountryInputDto countryInputDto = new CountryInputDto("CH", "China");
        CountrySimpleOutputDto outputCounty= new CountrySimpleOutputDto("CH", "China");

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        List<User> userList = List.of(user1);

        when(countryRepository.findById("CH")).thenReturn(Optional.ofNullable(country1));
        when(userRepository.findByCountryCode("CH")).thenReturn(userList);
        doNothing().when(countryRepository).deleteById("CH");

        countryService.deleteACountry("CH");

        assertEquals(null, user1.getCountry());
        verify(countryRepository, times(1)).findById("CH");
        verify(userRepository, times(1)).findByCountryCode("CH");
        verify(countryRepository, times(1)).deleteById("CH");

    }
}
package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.application.CountryService;
import com.example.angelneria_final_project.domain.entities.Country;
import com.example.angelneria_final_project.domain.entities.User;
import com.example.angelneria_final_project.domain.repositories.CountryRepository;
import com.example.angelneria_final_project.domain.repositories.UserRepository;
import com.example.angelneria_final_project.exceptions.DuplicatedCountryCodeException;
import com.example.angelneria_final_project.exceptions.UserNotFoundException;
import com.example.angelneria_final_project.infraestructure.dto.input.CountryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.CountrySimpleOutputDto;
import com.example.angelneria_final_project.mapper.CountryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    private Country findById(String id){
        return countryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There are no countries with code " + id + "."));
    }

    @Override
    public List<CountrySimpleOutputDto> getAllCountries() {
        return countryMapper.countryListToCountryDtoList(countryRepository.findAll());
    }

    @Override
    public CountrySimpleOutputDto getACountry(String id) {
        return countryMapper.countryToCountryDto(findById(id));
    }

    @Override
    public CountrySimpleOutputDto createACountry(CountryInputDto countryInputDto) {
        countryRepository.findById(countryInputDto.getCode()).ifPresent(country -> {
            throw new DuplicatedCountryCodeException("This code is already in use.");
        });
        return countryMapper.countryToCountryDto(countryRepository.save(countryMapper.countryDtoToCountry(countryInputDto)));
    }

    @Override
    public CountrySimpleOutputDto updateACountry(CountryInputDto countryInputDto, String id) {
        if(!Objects.isNull(countryInputDto.getCode()) && !id.equals(countryInputDto.getCode())){
            throw new DuplicatedCountryCodeException("You cannot update the country code.");
        }
        Country countryToUpdate = findById(id);
        countryMapper.updateCountryFromDto(countryInputDto, countryToUpdate);
        return countryMapper.countryToCountryDto(countryRepository.save(countryToUpdate));
    }

    @Override
    public void deleteACountry(String id) {
        Country country= findById(id);
        List<User> usuarios = userRepository.findByCountryCode(country.getCode());
        usuarios.forEach(user -> user.setCountry(null));
        countryRepository.deleteById(id);
    }
}

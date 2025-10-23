package com.example.angelneria_final_project.application;

import com.example.angelneria_final_project.infraestructure.dto.input.RegistryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.input.UserInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserSimpleOutputDto;

import java.util.List;

public interface UserService {

    List<UserSimpleOutputDto> getAllUsers();
    UserComplexOutputDto getAnUser(Long id);
    UserComplexOutputDto createAnUser(UserInputDto userInputDto);
    UserComplexOutputDto updateAnUser(UserInputDto userInputDto, Long id);
    void deleteAnUser(Long id);
    UserComplexOutputDto updateAnUserCountry(String countryId, Long userId);
    UserSimpleOutputDto registerUser(RegistryInputDto user);
}

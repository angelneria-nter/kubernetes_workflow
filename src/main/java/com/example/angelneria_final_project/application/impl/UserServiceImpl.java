package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.application.UserService;
import com.example.angelneria_final_project.domain.entities.Country;
import com.example.angelneria_final_project.domain.entities.User;
import com.example.angelneria_final_project.domain.repositories.CountryRepository;
import com.example.angelneria_final_project.domain.repositories.UserRepository;
import com.example.angelneria_final_project.exceptions.EmailAlreadyExistsException;
import com.example.angelneria_final_project.exceptions.ForbiddenOperationException;
import com.example.angelneria_final_project.exceptions.UserNotFoundException;
import com.example.angelneria_final_project.infraestructure.dto.input.RegistryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.input.UserInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserSimpleOutputDto;
import com.example.angelneria_final_project.mapper.UserMapper;
import com.example.angelneria_final_project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    private User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("There are no users with id " + id + "."));
    }

    private Country findCountryById(String id){
        return countryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There are no countries with code " + id + "."));
    }

    @Override
    public List<UserSimpleOutputDto> getAllUsers() {
        return userMapper.userListToUserDtoList(userRepository.findAll());
    }

    @Override
    public UserComplexOutputDto getAnUser(Long id) {
        return userMapper.userToComplexUserDto(findById(id));
    }

    @Override
    public UserComplexOutputDto createAnUser(UserInputDto userInputDto) {
        userRepository.findByEmailIncludingInactives(userInputDto.getEmail()).ifPresent(user -> {
            throw new EmailAlreadyExistsException("This email is already in use.");
        });

        User userToSave = userMapper.userDtoToUser(userInputDto);

        if(!Objects.isNull(userInputDto.getCountryCode())){
            Country country = findCountryById(userInputDto.getCountryCode());
            userToSave.setCountry(country);
        }

        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        return userMapper.userToComplexUserDto(userRepository.save(userToSave));
    }

    @Override
    public UserComplexOutputDto updateAnUser(UserInputDto userInputDto, Long id) {
        User userToUpdate = findById(id);
        Optional<User> userOptional= userRepository.findByEmailIncludingInactives(userInputDto.getEmail());
        if(!Objects.isNull(userInputDto.getEmail()) && userOptional.isPresent() && !userToUpdate.getEmail().equals(userInputDto.getEmail())){
            throw new EmailAlreadyExistsException("This email is already in use.");
        }
        userMapper.updateUserFromDto(userInputDto, userToUpdate);
        if(!Objects.isNull(userInputDto.getCountryCode())){
            Country country = findCountryById(userInputDto.getCountryCode());
            userToUpdate.setCountry(country);
        }
        return userMapper.userToComplexUserDto(userRepository.save(userToUpdate));
    }

    @Override
    public void deleteAnUser(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserComplexOutputDto updateAnUserCountry(String countryId, Long userId) {
        Authentication actualUser = SecurityContextHolder.getContext().getAuthentication();
        Country country = findCountryById(countryId);
        User user = findById(userId);
        if(!Objects.equals(user.getEmail(), actualUser.getName()) && actualUser.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))){
            throw new ForbiddenOperationException("You can only update your country.");
        }
        user.setCountry(country);
        return userMapper.userToComplexUserDto(userRepository.save(user));
    }

    @Override
    public UserSimpleOutputDto registerUser(RegistryInputDto user){
        userRepository.findByEmailIncludingInactives(user.getEmail()).ifPresent(u -> {
            throw new EmailAlreadyExistsException("This email is already in use.");
        });

        User userToRegister =userMapper.registryDtoToPerson(user);

        if(!Objects.isNull(user.getCountryCode())){
            Country country = findCountryById(user.getCountryCode());
            userToRegister.setCountry(country);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.userToUserDto(userRepository.save(userToRegister));
    }
}

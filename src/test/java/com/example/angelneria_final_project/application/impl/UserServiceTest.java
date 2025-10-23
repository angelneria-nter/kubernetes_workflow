package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.domain.entities.Country;
import com.example.angelneria_final_project.domain.entities.User;
import com.example.angelneria_final_project.domain.enums.Roles;
import com.example.angelneria_final_project.domain.repositories.CountryRepository;
import com.example.angelneria_final_project.domain.repositories.UserRepository;
import com.example.angelneria_final_project.exceptions.EmailAlreadyExistsException;
import com.example.angelneria_final_project.infraestructure.dto.input.RegistryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.input.UserInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserSimpleOutputDto;
import com.example.angelneria_final_project.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllUsers() {

        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        List<User> userList = List.of(user1);

        List<UserSimpleOutputDto> userSimpleOutputDto1 = List.of(new UserSimpleOutputDto(1L,"Prueba","prueba@nter.com",null, true));

        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.userListToUserDtoList(any(List.class))).thenReturn(userSimpleOutputDto1);

        List<UserSimpleOutputDto> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("prueba@nter.com", result.get(0).getEmail());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).userListToUserDtoList(userList);
    }

    @Test
    void getAnUser() {

        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        UserComplexOutputDto userComplexOutputDto1 = new UserComplexOutputDto(1l,"Prueba", "prueba@nter.com", null, true,null,null);

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        when(userMapper.userToComplexUserDto(user1)).thenReturn(userComplexOutputDto1);

        UserComplexOutputDto result = userService.getAnUser(1L);

        assertEquals("Prueba", result.getFullName());
        assertEquals("prueba@nter.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).userToComplexUserDto(user1);
    }

    @Test
    void createAnUser() {

        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        UserComplexOutputDto userComplexOutputDto1 = new UserComplexOutputDto(1l,"Prueba", "prueba@nter.com", null, true,null,null);
        UserInputDto userInputDto1 = new UserInputDto("Prueba","prueba@nter.com","12345678", true, "CH", Roles.USER);

        when(userRepository.findByEmailIncludingInactives("prueba@nter.com")).thenReturn(Optional.empty());
        when(userMapper.userDtoToUser(userInputDto1)).thenReturn(user1);
        when(countryRepository.findById("CH")).thenReturn(Optional.ofNullable(country1));
        when(passwordEncoder.encode("prueba123")).thenReturn("$2a$12$n/N6qJ/eRswq.uezPHwM5O34ITF4OerT91IMzkqtIjnG2XBIwBOOq");
        when(userRepository.save(user1)).thenReturn(user1);
        when(userMapper.userToComplexUserDto(user1)).thenReturn(userComplexOutputDto1);

        UserComplexOutputDto result = userService.createAnUser(userInputDto1);

        assertEquals("Prueba", result.getFullName());
        assertEquals("prueba@nter.com", result.getEmail());
        verify(userRepository, times(1)).findByEmailIncludingInactives("prueba@nter.com");
        verify(userMapper, times(1)).userDtoToUser(userInputDto1);
        verify(countryRepository, times(1)).findById("CH");
        verify(passwordEncoder, times(1)).encode("prueba123");
        verify(userRepository, times(1)).save(user1);
        verify(userMapper, times(1)).userToComplexUserDto(user1);
    }

    @Test
    void createAnUserWithExistentEmail(){
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        UserInputDto userInputDto1 = new UserInputDto("Prueba","prueba@nter.com","12345678", true, "CH", Roles.USER);

        when(userRepository.findByEmailIncludingInactives("prueba@nter.com")).thenReturn(Optional.ofNullable(user1));

        assertThrows(EmailAlreadyExistsException.class,() -> {userService.createAnUser(userInputDto1);});
    }

    @Test
    void updateAnUser() {
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        UserComplexOutputDto userComplexOutputDto1 = new UserComplexOutputDto(1l,"Prueba2", "prueba2@nter.com", null, true,null,null);
        UserInputDto userInputDto1 = new UserInputDto("Prueba2","prueba2@nter.com","12345678", true, "CH", Roles.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        when(countryRepository.findById("CH")).thenReturn(Optional.ofNullable(country1));
        when(userRepository.save(user1)).thenReturn(user1);
        when(userMapper.userToComplexUserDto(user1)).thenReturn(userComplexOutputDto1);

        UserComplexOutputDto result = userService.updateAnUser(userInputDto1, 1L);

        assertEquals("Prueba2", result.getFullName());
        assertEquals("prueba2@nter.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(countryRepository, times(1)).findById("CH");
        verify(userRepository, times(1)).save(user1);
        verify(userMapper, times(1)).userToComplexUserDto(user1);

    }

    @Test
    void deleteAnUser() {
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteAnUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateAnUserCountry() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "prueba@nter.com",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();


        UserComplexOutputDto userComplexOutputDto1 = new UserComplexOutputDto(1l,"Prueba2", "prueba2@nter.com", null, true,null,null);

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        when(countryRepository.findById("CH")).thenReturn(Optional.ofNullable(country1));
        when(userRepository.save(user1)).thenReturn(user1);
        when(userMapper.userToComplexUserDto(user1)).thenReturn(userComplexOutputDto1);

        UserComplexOutputDto result = userService.updateAnUserCountry("CH", 1L);

        assertEquals("Prueba2", result.getFullName());
        assertEquals("prueba2@nter.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(countryRepository, times(1)).findById("CH");
        verify(userRepository, times(1)).save(user1);
        verify(userMapper, times(1)).userToComplexUserDto(user1);


    }

    @Test
    void registerUser() {
        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("12345678")
                .isActive(true)
                .country(country1)
                .build();

        UserSimpleOutputDto userSimpleOutputDto = new UserSimpleOutputDto(1l,"Prueba", "prueba@nter.com", null, true);
        RegistryInputDto registryInputDto = new RegistryInputDto("Prueba","prueba@nter.com","12345678", "CH", Roles.USER);

        when(userRepository.findByEmailIncludingInactives(any(String.class))).thenReturn(Optional.empty());
        when(userMapper.registryDtoToPerson(registryInputDto)).thenReturn(user1);
        when(countryRepository.findById("CH")).thenReturn(Optional.ofNullable(country1));
        when(passwordEncoder.encode(any(String.class))).thenReturn("$2a$12$n/N6qJ/eRswq.uezPHwM5O34ITF4OerT91IMzkqtIjnG2XBIwBOOq");
        when(userRepository.save(user1)).thenReturn(user1);
        when(userMapper.userToUserDto(user1)).thenReturn(userSimpleOutputDto);

        UserSimpleOutputDto result = userService.registerUser(registryInputDto);

        assertEquals("Prueba", result.getFullName());
        assertEquals("prueba@nter.com", result.getEmail());
        verify(userRepository, times(1)).findByEmailIncludingInactives("prueba@nter.com");
        verify(userMapper, times(1)).registryDtoToPerson(registryInputDto);
        verify(countryRepository, times(1)).findById("CH");
        verify(passwordEncoder, times(1)).encode("12345678");
        verify(userRepository, times(1)).save(user1);
        verify(userMapper, times(1)).userToUserDto(user1);
    }
}
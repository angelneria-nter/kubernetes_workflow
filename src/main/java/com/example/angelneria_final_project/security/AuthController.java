package com.example.angelneria_final_project.security;

import com.example.angelneria_final_project.application.UserService;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.input.LoginDto;
import com.example.angelneria_final_project.infraestructure.dto.input.RegistryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserSimpleOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth operations")
public class AuthController {

    private final UserDetailsImpl userDetails;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Operation(description = "It is used to register into the app.")
    @PostMapping("/register")
    public ResponseEntity<UserSimpleOutputDto> createPerson(@RequestBody
                                                                @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the User using an RegistryInputDto.")
                                                                @Validated(CreateGroup.class) RegistryInputDto user){
        URI location= URI.create("/user");
        return ResponseEntity.created(location).body(userService.registerUser(user));
    }

    @Operation(description = "It is used to login into the app.")
    @PostMapping("/login")
    public String login(@RequestBody
                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the User using an LoginDto.")
                            @Valid LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(userDetails.loadUserByUsername(loginDto.getEmail()));
        }else{
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}

package com.example.angelneria_final_project.infraestructure.dto.input;

import com.example.angelneria_final_project.domain.enums.Roles;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistryInputDto {
    @NotBlank(groups = CreateGroup.class, message = "Full name must be present.")
    private String fullName;
    @NotBlank(groups = CreateGroup.class, message = "Full email must be present.")
    @Email(groups = {CreateGroup.class}, message = "You must use a valid email.")
    private String email;
    @NotBlank(groups = CreateGroup.class, message = "Password must be present.")
    @Size(min = 8,groups = {CreateGroup.class}, message = "Password must have 8 characters at least.")
    private String password;
    private String countryCode;
    private Roles rol;
}

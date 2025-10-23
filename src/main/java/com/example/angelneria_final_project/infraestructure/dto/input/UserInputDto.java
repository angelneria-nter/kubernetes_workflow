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
public class UserInputDto {

    @NotBlank(groups = CreateGroup.class, message = "Full name must be present.")
    private String fullName;
    @NotBlank(groups = CreateGroup.class, message = "Full email must be present.")
    @Email(groups = {CreateGroup.class, UpdateGroup.class}, message = "You must use a valid email.")
    private String email;
    @NotBlank(groups = CreateGroup.class, message = "Password must be present.")
    @Size(min = 8,groups = {CreateGroup.class, UpdateGroup.class}, message = "Password must have 8 characters at least.")
    private String password;
    @NotNull(groups = CreateGroup.class, message = "The isActive field must be present.")
    private Boolean isActive=true;
    private String countryCode;
    private Roles rol;

    @AssertTrue(groups = UpdateGroup.class, message = "Fullname must not be blank or empty.")
    private boolean isFullNameValid() {
        return Objects.isNull(this.fullName) || !this.fullName.trim().isEmpty();
    }

    @AssertTrue(groups = UpdateGroup.class, message = "Email must not be blank or empty.")
    private boolean isEmailValid() {
        return Objects.isNull(this.email) || !this.email.trim().isEmpty();
    }

    @AssertTrue(groups = UpdateGroup.class, message = "Password must not be blank or empty.")
    private boolean isPasswordValid() {
        return Objects.isNull(this.password) || !this.password.trim().isEmpty();
    }
}

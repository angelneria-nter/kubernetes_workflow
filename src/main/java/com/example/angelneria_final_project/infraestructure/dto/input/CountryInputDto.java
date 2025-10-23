package com.example.angelneria_final_project.infraestructure.dto.input;

import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryInputDto {

    @NotBlank(groups = CreateGroup.class, message = "Code must be present.")
    private String code;
    @NotBlank(groups = CreateGroup.class, message = "Name must be present.")
    private String name;

    @AssertTrue(groups = UpdateGroup.class, message = "Name must not be blank or empty.")
    private boolean isNameValid() {
        return Objects.isNull(this.name) || !this.name.trim().isEmpty();
    }

    @AssertTrue(groups = UpdateGroup.class, message = "Code must not be blank or empty.")
    private boolean isCodeValid() {
        return Objects.isNull(this.code) || !this.code.trim().isEmpty();
    }
}

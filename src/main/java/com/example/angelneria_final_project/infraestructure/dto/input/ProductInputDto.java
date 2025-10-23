package com.example.angelneria_final_project.infraestructure.dto.input;

import com.example.angelneria_final_project.domain.enums.ProductStatus;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInputDto {

    @NotBlank(message = "Name must be present.", groups = CreateGroup.class)
    private String name;
    @NotNull(message = "Price must be present.", groups = CreateGroup.class)
    private Float price;
    @NotNull(message = "Status must be present.", groups = CreateGroup.class)
    private ProductStatus productStatus;

    @AssertTrue(groups = UpdateGroup.class, message = "Name must not be blank or empty.")
    private boolean isNameValid() {
        return Objects.isNull(this.name) || !this.name.trim().isEmpty();
    }
}

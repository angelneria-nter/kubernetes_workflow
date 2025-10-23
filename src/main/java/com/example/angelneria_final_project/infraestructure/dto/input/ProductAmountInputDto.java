package com.example.angelneria_final_project.infraestructure.dto.input;

import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAmountInputDto {

    @NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "Product id must be present.")
    private Long productId;
    @NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "Amount must be present.")
    private Integer amount;
}

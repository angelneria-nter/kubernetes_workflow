package com.example.angelneria_final_project.infraestructure.dto.output;

import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountrySimpleOutputDto {
    private String code;
    private String name;

}

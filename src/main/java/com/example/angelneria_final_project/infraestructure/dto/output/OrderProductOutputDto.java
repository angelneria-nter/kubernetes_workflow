package com.example.angelneria_final_project.infraestructure.dto.output;

import com.example.angelneria_final_project.domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductOutputDto {

    private ProductSimpleOutputDto product;
    private Integer amount;
}

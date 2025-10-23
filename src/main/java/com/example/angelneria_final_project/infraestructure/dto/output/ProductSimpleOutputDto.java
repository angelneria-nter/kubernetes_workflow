package com.example.angelneria_final_project.infraestructure.dto.output;

import com.example.angelneria_final_project.domain.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleOutputDto {
    private Long id;
    private String name;
    private float price;
    private ProductStatus productStatus;
    private LocalDate createdAt;
}

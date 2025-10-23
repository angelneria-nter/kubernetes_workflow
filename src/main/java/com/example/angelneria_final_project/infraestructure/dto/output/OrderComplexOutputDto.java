package com.example.angelneria_final_project.infraestructure.dto.output;

import com.example.angelneria_final_project.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderComplexOutputDto {

    private Long id;
    private OrderStatus status;
    private Date createdAt;
    private UserSimpleOutputDto user;
    private List<OrderProductOutputDto> products;
}

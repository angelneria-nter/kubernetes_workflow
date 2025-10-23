package com.example.angelneria_final_project.application;

import com.example.angelneria_final_project.infraestructure.dto.input.OrderInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderSimpleOutputDto;

import java.util.List;

public interface OrderService {

    List<OrderSimpleOutputDto> getAllOrders();
    OrderComplexOutputDto getAOrder(Long id);
    OrderComplexOutputDto createAOrder(OrderInputDto orderInputDto);
    OrderComplexOutputDto updateAOrder(OrderInputDto orderInputDto, Long id);
}

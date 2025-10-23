package com.example.angelneria_final_project.application;

import com.example.angelneria_final_project.domain.entities.Order;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductAmountInputDto;

import java.util.List;

public interface OrderProductService {

    Order createOrderProduct(List<ProductAmountInputDto> productAmountInputDtos, Long orderId);
    void deleteOrderProducts(Long orderId);
}

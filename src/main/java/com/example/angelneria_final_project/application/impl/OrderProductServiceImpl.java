package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.application.OrderProductService;
import com.example.angelneria_final_project.domain.entities.*;
import com.example.angelneria_final_project.domain.repositories.OrderProductRepository;
import com.example.angelneria_final_project.domain.repositories.OrderRepository;
import com.example.angelneria_final_project.domain.repositories.ProductRepository;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductAmountInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    private Order findOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no order with id " + id + "."));
    }

    private Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no product with id " + id + "."));
    }

    @Override
    @Transactional
    public Order createOrderProduct(List<ProductAmountInputDto> productAmountInputDtos, Long orderId) {
        Order order = findOrderById(orderId);
        order.setProducts(new HashSet<>());
        productAmountInputDtos.forEach(productAmountInputDto -> {
            Product product = findProductById(productAmountInputDto.getProductId());
            OrderProductId orderProductId = new OrderProductId(orderId, product.getId());
            OrderProduct orderProduct = new OrderProduct(orderProductId, order, product, productAmountInputDto.getAmount());
            orderProduct=orderProductRepository.save(orderProduct);
            order.getProducts().add(orderProduct);
        });
        return order;
    }

    @Override
    public void deleteOrderProducts(Long orderId) {
        orderProductRepository.deleteAllByOrderId(orderId);
    }
}

package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.domain.entities.*;
import com.example.angelneria_final_project.domain.enums.OrderStatus;
import com.example.angelneria_final_project.domain.enums.ProductStatus;
import com.example.angelneria_final_project.domain.repositories.OrderProductRepository;
import com.example.angelneria_final_project.domain.repositories.OrderRepository;
import com.example.angelneria_final_project.domain.repositories.ProductRepository;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductAmountInputDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @InjectMocks
    private OrderProductServiceImpl orderProductService;

    @Test
    void createOrderProduct() {

        Country country1 = Country.builder().code("CH")
                .name("China")
                .build();

        User user1 = User.builder().id(1L)
                .email("prueba@nter.com")
                .fullName("Prueba")
                .password("prueba123")
                .isActive(true)
                .country(country1)
                .build();

        Product product1 = new Product(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());

        Order order = new Order(1L, OrderStatus.PENDING, new Date(), user1, null);

        OrderProductId orderProductId = new OrderProductId(1L,1L);

        OrderProduct orderProduct = new OrderProduct(orderProductId, order, product1, 2);

        order.setProducts(Set.of(orderProduct));

        ProductAmountInputDto productAmountInputDto = new ProductAmountInputDto(1L,2);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(orderProduct);

        Order result = orderProductService.createOrderProduct(List.of(productAmountInputDto), 1L);

        assertEquals(1, result.getProducts().size());
        verify(orderRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));

    }

    @Test
    void deleteOrderProducts() {
        doNothing().when(orderProductRepository).deleteAllByOrderId(1L);

        orderProductService.deleteOrderProducts(1L);

        verify(orderProductRepository, times(1)).deleteAllByOrderId(1L);
    }
}
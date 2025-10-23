package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.application.OrderProductService;
import com.example.angelneria_final_project.application.OrderService;
import com.example.angelneria_final_project.domain.entities.Order;
import com.example.angelneria_final_project.domain.entities.User;
import com.example.angelneria_final_project.domain.repositories.OrderRepository;
import com.example.angelneria_final_project.domain.repositories.ProductRepository;
import com.example.angelneria_final_project.domain.repositories.UserRepository;
import com.example.angelneria_final_project.exceptions.ForbiddenOperationException;
import com.example.angelneria_final_project.infraestructure.dto.input.OrderInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderSimpleOutputDto;
import com.example.angelneria_final_project.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderProductService orderProductService;
    private final ProductRepository productRepository;


    private Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no order with id " + id + "."));
    }

    private User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no user with id " + id + "."));
    }

    private void findProductById(Long id){
        productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no product with id " + id + "."));
    }

    @Override
    public List<OrderSimpleOutputDto> getAllOrders() {
        return orderMapper.orderListToOrderDtoList(orderRepository.findAll());
    }

    @Override
    public OrderComplexOutputDto getAOrder(Long id) {
        Order order = findById(id);
        Authentication actualUser = SecurityContextHolder.getContext().getAuthentication();
        if(!Objects.equals(actualUser.getName(), order.getUser().getEmail()) && actualUser.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))){
            throw new ForbiddenOperationException("You can only see your orders.");
        }
        return orderMapper.orderToOrderComplexDto(order);
    }

    @Override
    @Transactional
    public OrderComplexOutputDto createAOrder(OrderInputDto orderInputDto) {
        orderInputDto.getProducts().forEach(productAmountInputDto -> findProductById(productAmountInputDto.getProductId()));
        User user = findUserById(orderInputDto.getUserId());
        Order order = orderMapper.orderDtoToOrder(orderInputDto);
        Authentication actualUser = SecurityContextHolder.getContext().getAuthentication();
        if(!Objects.equals(actualUser.getName(), user.getEmail())){
            throw new ForbiddenOperationException("You cannot create orders for another person.");
        }
        order.setUser(user);
        order =orderRepository.save(order);
        return orderMapper.orderToOrderComplexDto(orderProductService.createOrderProduct(orderInputDto.getProducts(), order.getId()));
    }

    @Override
    @Transactional
    public OrderComplexOutputDto updateAOrder(OrderInputDto orderInputDto, Long id) {
        Order orderToUpdate = findById(id);
        orderMapper.updateOrderFromDto(orderInputDto, orderToUpdate);

        if(!Objects.isNull(orderInputDto.getUserId())){
            User user = findUserById(orderInputDto.getUserId());
            orderToUpdate.setUser(user);
        }

        if(!Objects.isNull(orderInputDto.getProducts())){
            orderInputDto.getProducts().forEach(productAmountInputDto -> findProductById(productAmountInputDto.getProductId()));
            orderProductService.deleteOrderProducts(id);
            orderProductService.createOrderProduct(orderInputDto.getProducts(), id);
        }
        return orderMapper.orderToOrderComplexDto(orderRepository.save(orderToUpdate));
    }

}

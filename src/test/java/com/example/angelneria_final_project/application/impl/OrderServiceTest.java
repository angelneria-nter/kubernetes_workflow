package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.domain.entities.*;
import com.example.angelneria_final_project.domain.enums.OrderStatus;
import com.example.angelneria_final_project.domain.enums.ProductStatus;
import com.example.angelneria_final_project.domain.repositories.OrderRepository;
import com.example.angelneria_final_project.domain.repositories.ProductRepository;
import com.example.angelneria_final_project.domain.repositories.UserRepository;
import com.example.angelneria_final_project.infraestructure.dto.input.OrderInputDto;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductAmountInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.*;
import com.example.angelneria_final_project.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderProductServiceImpl orderProductService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getAllOrders() {
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

        OrderSimpleOutputDto orderSimpleOutputDto = new OrderSimpleOutputDto(1L,OrderStatus.PENDING, new Date());

        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.orderListToOrderDtoList(List.of(order))).thenReturn(List.of(orderSimpleOutputDto));

        List<OrderSimpleOutputDto> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(OrderStatus.PENDING, result.get(0).getStatus());
        verify(orderRepository, times(1)).findAll();
        verify(orderMapper, times(1)).orderListToOrderDtoList(List.of(order));

    }

    @Test
    void getAOrder() {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "prueba@nter.com",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

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

        UserSimpleOutputDto userSimpleOutputDto = new UserSimpleOutputDto(1L, "Prueba", "prueba@nter.com", new Date(), true);

        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        OrderProductOutputDto orderProductOutputDto = new OrderProductOutputDto(productSimpleOutputDto,2);
        OrderComplexOutputDto orderComplexOutputDto = new OrderComplexOutputDto(1L,OrderStatus.PENDING, new Date(), userSimpleOutputDto, List.of(orderProductOutputDto));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderComplexDto(order)).thenReturn(orderComplexOutputDto);

        OrderComplexOutputDto result = orderService.getAOrder(1L);

        assertEquals(2, result.getProducts().get(0).getAmount());
        assertEquals(1, result.getProducts().size());
        assertEquals("Prueba", result.getUser().getFullName());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderMapper, times(1)).orderToOrderComplexDto(order);


    }

    @Test
    void createAOrder() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "prueba@nter.com",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

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

        UserSimpleOutputDto userSimpleOutputDto = new UserSimpleOutputDto(1L, "Prueba", "prueba@nter.com", new Date(), true);

        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        OrderProductOutputDto orderProductOutputDto = new OrderProductOutputDto(productSimpleOutputDto,2);
        OrderComplexOutputDto orderComplexOutputDto = new OrderComplexOutputDto(1L,OrderStatus.PENDING, new Date(), userSimpleOutputDto, List.of(orderProductOutputDto));
        ProductAmountInputDto productAmountInputDto = new ProductAmountInputDto(1L,2);
        OrderInputDto orderInputDto = new OrderInputDto(OrderStatus.PENDING,1L, List.of(productAmountInputDto));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        when(orderMapper.orderDtoToOrder(orderInputDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderProductService.createOrderProduct(List.of(productAmountInputDto),1L)).thenReturn(order);
        when(orderMapper.orderToOrderComplexDto(order)).thenReturn(orderComplexOutputDto);

        OrderComplexOutputDto result = orderService.createAOrder(orderInputDto);

        assertEquals(2, result.getProducts().get(0).getAmount());
        assertEquals(1, result.getProducts().size());
        assertEquals("Prueba", result.getUser().getFullName());
        verify(productRepository,times(1)).findById(1L);
        verify(userRepository,times(1)).findById(1L);
        verify(orderMapper,times(1)).orderDtoToOrder(orderInputDto);
        verify(orderRepository,times(1)).save(order);
        verify(orderProductService,times(1)).createOrderProduct(List.of(productAmountInputDto),1L);
        verify(orderMapper,times(1)).orderToOrderComplexDto(order);

    }

    @Test
    void updateAOrder() {

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

        UserSimpleOutputDto userSimpleOutputDto = new UserSimpleOutputDto(1L, "Prueba", "prueba@nter.com", new Date(), true);

        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        OrderProductOutputDto orderProductOutputDto = new OrderProductOutputDto(productSimpleOutputDto,5);
        OrderComplexOutputDto orderComplexOutputDto = new OrderComplexOutputDto(1L,OrderStatus.DELIVERED, new Date(), userSimpleOutputDto, List.of(orderProductOutputDto));
        ProductAmountInputDto productAmountInputDto = new ProductAmountInputDto(1L,2);
        OrderInputDto orderInputDto = new OrderInputDto(OrderStatus.PENDING,1L, List.of(productAmountInputDto));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(orderProductService.createOrderProduct(List.of(productAmountInputDto),1L)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.orderToOrderComplexDto(order)).thenReturn(orderComplexOutputDto);

        OrderComplexOutputDto result = orderService.updateAOrder(orderInputDto, 1L);

        assertEquals(5, result.getProducts().get(0).getAmount());
        assertEquals(1, result.getProducts().size());
        assertEquals(OrderStatus.DELIVERED, result.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(userRepository,times(1)).findById(1L);
        verify(orderProductService, times(1)).createOrderProduct(List.of(productAmountInputDto), 1L);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).orderToOrderComplexDto(order);

    }
}
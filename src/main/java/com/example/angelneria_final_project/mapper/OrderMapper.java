package com.example.angelneria_final_project.mapper;

import com.example.angelneria_final_project.domain.entities.Order;
import com.example.angelneria_final_project.infraestructure.dto.input.OrderInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderSimpleOutputDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderProductMapper.class})
public interface OrderMapper {

    List<OrderSimpleOutputDto> orderListToOrderDtoList(List<Order> orderList);

    OrderSimpleOutputDto orderToOrderDto(Order order);

    OrderComplexOutputDto orderToOrderComplexDto(Order order);

    @Mapping(target = "products", ignore = true)
    Order orderDtoToOrder(OrderInputDto orderInputDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(OrderInputDto orderInputDto, @MappingTarget Order order);
}

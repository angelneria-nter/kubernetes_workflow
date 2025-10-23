package com.example.angelneria_final_project.mapper;

import com.example.angelneria_final_project.domain.entities.OrderProduct;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderProductOutputDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderProductMapper {

    List<OrderProductOutputDto> orderProductListToOrderProductDtoList(List<OrderProduct> orderProductList);
}

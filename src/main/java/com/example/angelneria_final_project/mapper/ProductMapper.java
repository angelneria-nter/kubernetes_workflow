package com.example.angelneria_final_project.mapper;

import com.example.angelneria_final_project.domain.entities.Product;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.ProductSimpleOutputDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<ProductSimpleOutputDto> productListToProductDtoList(List<Product> productList);

    ProductSimpleOutputDto productToProductDto(Product product);

    Product productDtoToProduct(ProductInputDto productInputDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductInputDto productInputDto, @MappingTarget Product product);
}

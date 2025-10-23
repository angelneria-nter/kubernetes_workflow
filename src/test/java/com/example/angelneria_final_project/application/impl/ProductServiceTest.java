package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.domain.entities.Product;
import com.example.angelneria_final_project.domain.enums.ProductStatus;
import com.example.angelneria_final_project.domain.repositories.OrderProductRepository;
import com.example.angelneria_final_project.domain.repositories.ProductRepository;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.ProductSimpleOutputDto;
import com.example.angelneria_final_project.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts() {
        Product product1 = new Product(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        List<Product> products= List.of(product1);
        List<ProductSimpleOutputDto> productSimpleOutputDtos = List.of(productSimpleOutputDto);
        HashMap<String, Object> data = new HashMap<>();

        when(productRepository.findFilteredProducts(data)).thenReturn(products);
        when(productMapper.productListToProductDtoList(products)).thenReturn(productSimpleOutputDtos);

        List<ProductSimpleOutputDto> result = productService.getAllProducts(null,null,null,null);

        assertEquals(1, result.size());
        assertEquals("Ventilador", result.get(0).getName());
        verify(productRepository, times(1)).findFilteredProducts(data);
        verify(productMapper, times(1)).productListToProductDtoList(products);
    }

    @Test
    void getAProduct() {
        Product product1 = new Product(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productMapper.productToProductDto(product1)).thenReturn(productSimpleOutputDto);

        ProductSimpleOutputDto result = productService.getAProduct(1L);

        assertEquals("Ventilador", result.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).productToProductDto(product1);

    }

    @Test
    void createAProduct() {
        Product product1 = new Product(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductInputDto productInputDto = new ProductInputDto("Ventilador", 12.4F, ProductStatus.ARCHIVED);

        when(productMapper.productDtoToProduct(productInputDto)).thenReturn(product1);
        when(productRepository.save(product1)).thenReturn(product1);
        when(productMapper.productToProductDto(product1)).thenReturn(productSimpleOutputDto);

        ProductSimpleOutputDto result = productService.createAProduct(productInputDto);
        assertEquals("Ventilador", result.getName());
        verify(productMapper, times(1)).productDtoToProduct(productInputDto);
        verify(productRepository, times(1)).save(product1);
        verify(productMapper, times(1)).productToProductDto(product1);
    }

    @Test
    void updateAProduct() {
        Product product1 = new Product(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 15F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductInputDto productInputDto = new ProductInputDto("Ventilador", 12.4F, ProductStatus.ARCHIVED);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(product1)).thenReturn(product1);
        when(productMapper.productToProductDto(product1)).thenReturn(productSimpleOutputDto);

        ProductSimpleOutputDto result = productService.updateAProduct(productInputDto, 1L);

        assertEquals(15L, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product1);
        verify(productMapper, times(1)).productToProductDto(product1);

    }

    @Test
    void deleteAProduct() {
        Product product1 = new Product(1L,"Ventilador", 12.4F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductSimpleOutputDto productSimpleOutputDto = new ProductSimpleOutputDto(1L,"Ventilador", 15F, ProductStatus.ARCHIVED, LocalDate.now());
        ProductInputDto productInputDto = new ProductInputDto("Ventilador", 12.4F, ProductStatus.ARCHIVED);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(orderProductRepository.findByProductId(1L)).thenReturn(new ArrayList<>());
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteAProduct(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(orderProductRepository, times(1)).findByProductId(1L);
    }
}
package com.example.angelneria_final_project.application.impl;

import com.example.angelneria_final_project.application.ProductService;
import com.example.angelneria_final_project.domain.entities.Country;
import com.example.angelneria_final_project.domain.entities.Product;
import com.example.angelneria_final_project.domain.repositories.OrderProductRepository;
import com.example.angelneria_final_project.domain.repositories.ProductRepository;
import com.example.angelneria_final_project.exceptions.InvalidProductDeletion;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.ProductSimpleOutputDto;
import com.example.angelneria_final_project.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    private Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There are no products with id " + id + "."));
    }

    @Override
    public List<ProductSimpleOutputDto> getAllProducts(String name, Float price, LocalDate initDate, LocalDate finishDate) {
        HashMap<String, Object> data = new HashMap<>();
        if (!Objects.isNull(name)) data.put("name", name);
        if (!Objects.isNull(price)) data.put("price", price);
        if (!Objects.isNull(initDate)) data.put("initDate", initDate);
        if (!Objects.isNull(finishDate)) data.put("finishDate", finishDate);
        return productMapper.productListToProductDtoList(productRepository.findFilteredProducts(data));
    }

    @Override
    public ProductSimpleOutputDto getAProduct(Long id) {
        return productMapper.productToProductDto(findById(id));
    }

    @Override
    public ProductSimpleOutputDto createAProduct(ProductInputDto productInputDto) {
        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(productInputDto)));
    }

    @Override
    public ProductSimpleOutputDto updateAProduct(ProductInputDto productInputDto, Long id) {
        Product productToUpdate = findById(id);
        productMapper.updateProductFromDto(productInputDto, productToUpdate);
        return productMapper.productToProductDto(productRepository.save(productToUpdate));
    }

    @Override
    public void deleteAProduct(Long id) {
        findById(id);
        if(!orderProductRepository.findByProductId(id).isEmpty()){
            throw new InvalidProductDeletion("The product is part of an order.");
        }
        productRepository.deleteById(id);
    }
}

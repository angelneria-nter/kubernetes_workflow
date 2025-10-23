package com.example.angelneria_final_project.application;

import com.example.angelneria_final_project.infraestructure.dto.input.ProductInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.ProductSimpleOutputDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ProductService {

    List<ProductSimpleOutputDto> getAllProducts(String name, Float price, LocalDate initDate, LocalDate finishDate);
    ProductSimpleOutputDto getAProduct(Long id);
    ProductSimpleOutputDto createAProduct(ProductInputDto productInputDto);
    ProductSimpleOutputDto updateAProduct(ProductInputDto productInputDto, Long id);
    void deleteAProduct(Long id);
}

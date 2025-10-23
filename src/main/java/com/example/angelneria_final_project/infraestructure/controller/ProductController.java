package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.application.ProductService;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import com.example.angelneria_final_project.infraestructure.dto.input.ProductInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.ProductSimpleOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Product operations")
public class ProductController {
    
    private final ProductService productService;

    @Operation(summary = "You need to be admin or user to access.", description = "It returns all the products.")
    @GetMapping
    ResponseEntity<List<ProductSimpleOutputDto>> getAllProducts(
            @Parameter(description = "The name of the product to filter.")@RequestParam(required = false) String name,
            @Parameter(description = "The price of the product to filter.")@RequestParam(required = false) Float price,
            @Parameter(description = "The initial date of the product to filter.")@RequestParam(required = false) LocalDate initDate,
            @Parameter(description = "The finish date of the product to filter.")@RequestParam(required = false) LocalDate finishDate
    ){
        return ResponseEntity.ok().body(productService.getAllProducts(name,price,initDate,finishDate));
    }

    @Operation(summary = "You need to be admin or user to access.", description = "It returns a specific product.")
    @GetMapping("/{id}")
    ResponseEntity<ProductSimpleOutputDto> getAProduct(@Parameter(name = "id", description = "The id of the specific product.")@PathVariable Long id){
        return ResponseEntity.ok().body(productService.getAProduct(id));
    }

    @Operation(summary = "You need to be admin to access.", description = "It creates a product.")
    @PostMapping
    ResponseEntity<ProductSimpleOutputDto> postAProduct(@Validated(CreateGroup.class)
                                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the Product using a ProductInputDto.")
                                                        @RequestBody ProductInputDto productInputDto){
        URI location = URI.create("/product");
        return ResponseEntity.created(location).body(productService.createAProduct(productInputDto));
    }

    @Operation(summary = "You need to be admin to access.", description = "It updates a product.")
    @PatchMapping("/{id}")
    ResponseEntity<ProductSimpleOutputDto> patchAProduct(@Validated(UpdateGroup.class)
                                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the Product using a ProductInputDto.")
                                                         @RequestBody ProductInputDto productInputDto,
                                                         @Parameter(name = "id", description = "The id of the specific product.")@PathVariable Long id){
        return ResponseEntity.ok().body(productService.updateAProduct(productInputDto, id));
    }

    @Operation(summary = "You need to be admin to access.", description = "It deletes a product.")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAProduct(@Parameter(name = "id", description = "The id of the specific product.")@PathVariable Long id){
        productService.deleteAProduct(id);
        return ResponseEntity.noContent().build();
    }
}

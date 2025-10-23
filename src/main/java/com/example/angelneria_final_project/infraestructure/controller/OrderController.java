package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.application.OrderService;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import com.example.angelneria_final_project.infraestructure.dto.input.OrderInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.OrderSimpleOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Order operations")
public class OrderController {
    
    private final OrderService orderService;

    @Operation(summary = "You need to be admin to access.", description = "It returns all the orders.")
    @GetMapping
    ResponseEntity<List<OrderSimpleOutputDto>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @Operation(summary = "You need to be admin or user to access to your orders.", description = "It returns an order if your user is admin or the owner of the order.")
    @GetMapping("/{id}")
    ResponseEntity<OrderComplexOutputDto> getAnOrder(@Parameter(name = "id", description = "The id of a specific order.")@PathVariable Long id){
        return ResponseEntity.ok().body(orderService.getAOrder(id));
    }

    @Operation(summary = "You need to be the owner user of the order.", description = "It creates an order if your user is the owner of the order.")
    @PostMapping
    ResponseEntity<OrderComplexOutputDto> postAnOrder(@Validated(CreateGroup.class)
                                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the Order using a OrderInputDto.")
                                                      @RequestBody OrderInputDto orderInputDto){
        URI location = URI.create("/order");
        return ResponseEntity.created(location).body(orderService.createAOrder(orderInputDto));
    }

    @Operation(summary = "You need to be admin to access.", description = "It updates an order.")
    @PatchMapping("/{id}")
    ResponseEntity<OrderComplexOutputDto> patchAnOrder(@Validated(UpdateGroup.class)
                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the Order using a OrderInputDto.")
                                                       @RequestBody OrderInputDto orderInputDto,
                                                       @Parameter(name = "id", description = "The id of a specific order.")@PathVariable Long id){
        return ResponseEntity.ok().body(orderService.updateAOrder(orderInputDto, id));
    }

}

package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.application.CountryService;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import com.example.angelneria_final_project.infraestructure.dto.input.CountryInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.CountrySimpleOutputDto;
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
@RequestMapping("/countries")
@RequiredArgsConstructor
@Tag(name = "Country operations")
public class CountryController {
    private final CountryService countryService;

    @Operation(summary = "You need to be admin or user to access.", description = "It returns all the countries.")
    @GetMapping
    ResponseEntity<List<CountrySimpleOutputDto>> getAllCountries(){
        return ResponseEntity.ok().body(countryService.getAllCountries());
    }

    @Operation(summary = "You need to be admin or user to access.", description = "It returns the specified country.")
    @GetMapping("/{id}")
    ResponseEntity<CountrySimpleOutputDto> getACountry(@Parameter(name = "id", description = "The id of a specific country.") @PathVariable String id){
        return ResponseEntity.ok().body(countryService.getACountry(id));
    }

    @Operation(summary = "You need to be admin to access.", description = "It creates a country using the input data.")
    @PostMapping
    ResponseEntity<CountrySimpleOutputDto> postACountry(@Validated(CreateGroup.class)
                                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the Country using a CountryInputDto.")
                                                        @RequestBody CountryInputDto countryInputDto){
        URI location = URI.create("/country");
        return ResponseEntity.created(location).body(countryService.createACountry(countryInputDto));
    }

    @Operation(summary = "You need to be admin to access.", description = "It updates a country using the input data.")
    @PatchMapping("/{id}")
    ResponseEntity<CountrySimpleOutputDto> patchACountry(@Validated(UpdateGroup.class)
                                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the new data of the Country using a CountryInputDto.")
                                                         @RequestBody CountryInputDto countryInputDto,
                                                         @Parameter(name = "id", description = "The id of a specific country.") @PathVariable String id){
        return ResponseEntity.ok().body(countryService.updateACountry(countryInputDto, id));
    }

    @Operation(summary = "You need to be admin to access.", description = "It deletes a country.")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteACountry(@Parameter(name = "id", description = "The id of a specific country.") @PathVariable String id){
        countryService.deleteACountry(id);
        return ResponseEntity.noContent().build();
    }
}

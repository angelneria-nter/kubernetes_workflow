package com.example.angelneria_final_project.infraestructure.controller;

import com.example.angelneria_final_project.application.UserService;
import com.example.angelneria_final_project.infraestructure.dto.groups.CreateGroup;
import com.example.angelneria_final_project.infraestructure.dto.groups.UpdateGroup;
import com.example.angelneria_final_project.infraestructure.dto.input.UserInputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserComplexOutputDto;
import com.example.angelneria_final_project.infraestructure.dto.output.UserSimpleOutputDto;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User operations")
public class UserController {
    private final UserService userService;

    @Operation(summary = "You need to be admin to access.", description = "It returns all the users.")
    @GetMapping
    ResponseEntity<List<UserSimpleOutputDto>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @Operation(summary = "You need to be admin to access.", description = "It returns a specific user.")
    @GetMapping("/{id}")
    ResponseEntity<UserComplexOutputDto> getAnUser(@Parameter(name = "id", description = "The id of the specific user.")@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getAnUser(id));
    }

    @Operation(summary = "You need to be admin to access.", description = "It creates a new user.")
    @PostMapping
    ResponseEntity<UserComplexOutputDto> postAnUser(@Validated(CreateGroup.class)
                                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the User using a UserInputDto.")
                                                    @RequestBody UserInputDto userInputDto){
        URI location = URI.create("/user");
        return ResponseEntity.created(location).body(userService.createAnUser(userInputDto));
    }

    @Operation(summary = "You need to be admin to access.", description = "It updates a specific user.")
    @PatchMapping("/{id}")
    ResponseEntity<UserComplexOutputDto> patchAnUser(@Validated(UpdateGroup.class)
                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "You have to introduce the data of the User using an UserInputDto.")
                                                     @RequestBody UserInputDto userInputDto,
                                                     @Parameter(name = "id", description = "The id of the specific product.")@PathVariable Long id){
        return ResponseEntity.ok().body(userService.updateAnUser(userInputDto, id));
    }

    @Operation(summary = "You need to be admin to access.", description = "It deletes a specific user.")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAnUser(@Parameter(name = "id", description = "The id of the specific product.")@PathVariable Long id){
        userService.deleteAnUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "You need to be admin or the owner user to access.", description = "It updates the country of your user.")
    @PatchMapping("/{id}/country")
    ResponseEntity<UserComplexOutputDto> patchAnUserCountry(@Parameter(description = "The id of the specific country.")@RequestHeader String countryId,
                                                            @Parameter(name = "id", description = "The id of the specific product.")@PathVariable Long id){
        return ResponseEntity.ok().body(userService.updateAnUserCountry(countryId,id));
    }
}

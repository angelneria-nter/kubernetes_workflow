package com.example.angelneria_final_project.infraestructure.dto.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserComplexOutputDto {

    private Long id;
    private String fullName;
    private String email;
    private Date createdAt;
    private Boolean isActive;
    private CountrySimpleOutputDto country;
    private List<OrderSimpleOutputDto> orders;
}

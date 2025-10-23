package com.example.angelneria_final_project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CustomError {
    private Date timestamp;
    private int httpCode;
    private String message;
}

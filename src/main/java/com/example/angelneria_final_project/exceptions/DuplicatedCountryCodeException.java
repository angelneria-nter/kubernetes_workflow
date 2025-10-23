package com.example.angelneria_final_project.exceptions;

public class DuplicatedCountryCodeException extends RuntimeException {
    public DuplicatedCountryCodeException(String message) {
        super(message);
    }
}

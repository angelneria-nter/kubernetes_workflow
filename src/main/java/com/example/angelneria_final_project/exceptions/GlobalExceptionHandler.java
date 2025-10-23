package com.example.angelneria_final_project.exceptions;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    protected  ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected  ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicatedCountryCodeException.class)
    protected  ResponseEntity<Object> handleDuplicatedCountryCodeException(DuplicatedCountryCodeException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotValidException.class)
    protected  ResponseEntity<Object> handleEmailNotValidException(EmailNotValidException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    protected  ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidFullnameException.class)
    protected  ResponseEntity<Object> handleInvalidFullnameException(InvalidFullnameException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    protected  ResponseEntity<Object> handleForbiddenOperationException(ForbiddenOperationException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidProductDeletion.class)
    protected  ResponseEntity<Object> handleInvalidProductDeletion(InvalidProductDeletion ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected  ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex){
        CustomError customError = new CustomError(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(customError,HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        CustomError customError = new CustomError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage().split(":")[2].trim().concat(ex.getMessage().split(":")[3]));
        return new ResponseEntity<>(customError,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        String fieldName = ex.getBindingResult().getFieldError().getField();
        String error = ex.getBindingResult().getFieldError().getDefaultMessage();

        switch (fieldName) {
            case "password" -> {
                return handleInvalidPasswordException(new InvalidPasswordException(error));
            }
            case "fullName" -> {
                return handleInvalidFullnameException(new InvalidFullnameException(error));
            }
            case "email" -> {
                return handleEmailNotValidException(new EmailNotValidException(error));
            }
            default -> {
                CustomError customError = new CustomError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), error);
                return new ResponseEntity<>(customError,HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }

    }
}

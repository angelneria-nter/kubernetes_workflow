package com.example.angelneria_final_project.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;


    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("Usuario no encontrado");

        ResponseEntity<Object> responseEntity = handler.handleUserNotFoundException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(404, customError.getHttpCode());
        assertEquals("Usuario no encontrado", customError.getMessage());
    }

    @Test
    void handleEmailAlreadyExistsException() {
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException("El email está en uso");

        ResponseEntity<Object> responseEntity = handler.handleEmailAlreadyExistsException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(409, customError.getHttpCode());
        assertEquals("El email está en uso", customError.getMessage());
    }

    @Test
    void handleDuplicatedCountryCodeException() {
        DuplicatedCountryCodeException exception = new DuplicatedCountryCodeException("Ya existe ese país");

        ResponseEntity<Object> responseEntity = handler.handleDuplicatedCountryCodeException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(409, customError.getHttpCode());
        assertEquals("Ya existe ese país", customError.getMessage());
    }

    @Test
    void handleEmailNotValidException() {
        EmailNotValidException exception = new EmailNotValidException("El email no es válido");

        ResponseEntity<Object> responseEntity = handler.handleEmailNotValidException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(422, customError.getHttpCode());
        assertEquals("El email no es válido", customError.getMessage());
    }

    @Test
    void handleInvalidPasswordException() {
        InvalidPasswordException exception = new InvalidPasswordException("La contraseña no es válida");

        ResponseEntity<Object> responseEntity = handler.handleInvalidPasswordException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(422, customError.getHttpCode());
        assertEquals("La contraseña no es válida", customError.getMessage());
    }

    @Test
    void handleInvalidFullnameException() {
        InvalidFullnameException exception = new InvalidFullnameException("El nombre no es válido");

        ResponseEntity<Object> responseEntity = handler.handleInvalidFullnameException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(422, customError.getHttpCode());
        assertEquals("El nombre no es válido", customError.getMessage());
    }

    @Test
    void handleForbiddenOperationException() {
        ForbiddenOperationException exception = new ForbiddenOperationException("La operación no está permitida");

        ResponseEntity<Object> responseEntity = handler.handleForbiddenOperationException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(403, customError.getHttpCode());
        assertEquals("La operación no está permitida", customError.getMessage());
    }

    @Test
    void handleInvalidProductDeletion() {
        InvalidProductDeletion exception = new InvalidProductDeletion("No se puede eliminar el producto");

        ResponseEntity<Object> responseEntity = handler.handleInvalidProductDeletion(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(409, customError.getHttpCode());
        assertEquals("No se puede eliminar el producto", customError.getMessage());
    }

    @Test
    void handleNoSuchElementException() {
        NoSuchElementException exception = new NoSuchElementException("No existe el elemento");

        ResponseEntity<Object> responseEntity = handler.handleNoSuchElementException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(404, customError.getHttpCode());
        assertEquals("No existe el elemento", customError.getMessage());
    }

    @Test
    void handleHttpMessageNotReadable() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Existe: un :error en la: cadena");

        ResponseEntity<Object> responseEntity = handler.handleHttpMessageNotReadable(exception, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, null);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError);
        assertEquals(422, customError.getHttpCode());
        assertEquals("error en la cadena", customError.getMessage());
    }

    @Test
    void handleMethodArgumentNotValid() {

        String objectName = "userDto";
        String fieldName = "username";
        String errorMessage = "El nombre de usuario no puede estar vacío.";

        FieldError fieldError = new FieldError(objectName, fieldName, errorMessage);


        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), objectName);
        bindingResult.addError(fieldError);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);


        ResponseEntity<Object> responseEntity = handler.handleMethodArgumentNotValid(
                exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, null
        );

        assertNotNull(responseEntity, "La respuesta no debería ser nula.");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode(), "El código de estado debe ser 422.");

        CustomError customError = (CustomError) responseEntity.getBody();
        assertNotNull(customError, "El cuerpo de la respuesta no debería ser nulo.");
        assertEquals(422, customError.getHttpCode(), "El código en el cuerpo del error debe ser 422.");
        assertEquals(errorMessage, customError.getMessage(), "El mensaje de error no coincide con el esperado.");
    }
}

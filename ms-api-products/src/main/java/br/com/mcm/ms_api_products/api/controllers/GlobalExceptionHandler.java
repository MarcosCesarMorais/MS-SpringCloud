package br.com.mcm.ms_api_products.api.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.mcm.ms_api_products.exceptions.Field;
import br.com.mcm.ms_api_products.exceptions.ObjectNotFoundException;
import br.com.mcm.ms_api_products.exceptions.StandardError;
import br.com.mcm.ms_api_products.exceptions.StandardErrorType;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> handlerConstraintViolationException(ConstraintViolationException ex) {
        StandardErrorType type = StandardErrorType.INVALID_PARAMETER;
        LocalDateTime dateTime = LocalDateTime.now();
        String detail = "One or more fields are invalid. Please check and try again.";

        List<Field> fields = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            fields.add(new Field(fieldName, errorMessage));
        });
        StandardError standardError = new StandardError(dateTime, type, detail, fields);
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFoundException(ObjectNotFoundException ex) {
        StandardErrorType type = StandardErrorType.RESOURCE_NOT_FOUND;
        LocalDateTime dateTime = LocalDateTime.now();
        String detail = ex.getMessage();

        StandardError standardError = new StandardError(dateTime, type, detail);
        return new ResponseEntity<>(standardError, HttpStatus.NOT_FOUND);
    }
}

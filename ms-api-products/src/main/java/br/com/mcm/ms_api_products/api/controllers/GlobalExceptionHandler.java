package br.com.mcm.ms_api_products.api.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.mcm.ms_api_products.application.exceptions.Field;
import br.com.mcm.ms_api_products.application.exceptions.ObjectNotFoundException;
import br.com.mcm.ms_api_products.application.exceptions.StandardError;
import br.com.mcm.ms_api_products.application.exceptions.StandardErrorType;

import org.springframework.http.HttpHeaders;
import org.springframework.validation.FieldError;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> handlerConstraintViolationException(ConstraintViolationException ex) {
        String type = StandardErrorType.INVALID_PARAMETER.toString();
        LocalDateTime dateTime = LocalDateTime.now();
        String detail = "One or more fields are invalid. Please check and try again.";
        List<Field> fields = new ArrayList<>();

        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            fields.add(new Field(fieldName, errorMessage));
        });
        var standardError = defaultErrorInstance(dateTime, type, detail, fields);
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<StandardError> handlerUnexpectedTypeException(UnexpectedTypeException ex) {
        String type = StandardErrorType.INVALID_PARAMETER.toString();
        LocalDateTime dateTime = LocalDateTime.now();
        String detail = "The provided data type is unexpected. Please check the input type.";
        List<Field> fields = new ArrayList<>();

        var standardError = defaultErrorInstance(dateTime, type, detail, fields);
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String type = StandardErrorType.INVALID_PARAMETER.toString();
        LocalDateTime dateTime = LocalDateTime.now();
        String detail = "Please check if the fields have valid values.";
        List<Field> fields = ex.getAllErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError,
                            LocaleContextHolder.getLocale());
                    String name = fieldError.getObjectName();
                    if (fieldError instanceof FieldError) {
                        name = ((FieldError) fieldError).getField();
                    }
                    return new Field(
                            name,
                            message);
                })
                .collect(Collectors.toList());

        var standardError = defaultErrorInstance(dateTime, type, detail, fields);
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFoundException(ObjectNotFoundException ex) {
        String type = StandardErrorType.RESOURCE_NOT_FOUND.toString();
        LocalDateTime dateTime = LocalDateTime.now();
        List<Field> fields = new ArrayList<>();
        String detail = ex.getMessage();

        var standardError = defaultErrorInstance(dateTime, type, detail, fields);
        return new ResponseEntity<>(standardError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String type = StandardErrorType.INVALID_PARAMETER.toString();
        LocalDateTime dateTime = LocalDateTime.now();
        String detail = ex.getMessage();
        List<Field> fields = new ArrayList<>();
        var standardError = defaultErrorInstance(dateTime, type, detail, fields);
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    private StandardError defaultErrorInstance(LocalDateTime dateTime, String type, String detail,
            List<Field> fields) {
        return new StandardError(
                dateTime,
                type,
                detail,
                fields);
    }
}

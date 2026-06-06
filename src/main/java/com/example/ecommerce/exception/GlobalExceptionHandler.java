package com.example.ecommerce.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse>
    handleNotFound(
            ResourceNotFoundException ex){

        return ResponseEntity.status(
                        HttpStatus.NOT_FOUND)
                .body(
                        new ApiErrorResponse(
                                LocalDateTime.now(),
                                404,
                                "NOT_FOUND",
                                List.of(
                                        ex.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(
            BusinessException.class)
    public ResponseEntity<ApiErrorResponse>
    handleBusiness(
            BusinessException ex){

        return ResponseEntity.badRequest()
                .body(
                        new ApiErrorResponse(
                                LocalDateTime.now(),
                                400,
                                "BUSINESS_ERROR",
                                List.of(
                                        ex.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex){

        List<String> errors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(
                                FieldError::getDefaultMessage
                        )
                        .toList();

        return ResponseEntity.badRequest()
                .body(
                        new ApiErrorResponse(
                                LocalDateTime.now(),
                                400,
                                "VALIDATION_ERROR",
                                errors
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse>
    handleGeneral(
            Exception ex){

        return ResponseEntity.internalServerError()
                .body(
                        new ApiErrorResponse(
                                LocalDateTime.now(),
                                500,
                                "INTERNAL_SERVER_ERROR",
                                List.of(
                                        ex.getMessage()
                                )
                        )
                );
    }
}
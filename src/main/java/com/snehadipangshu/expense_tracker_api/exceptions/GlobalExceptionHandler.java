package com.snehadipangshu.expense_tracker_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

//  @RestControllerAdvice tells Spring :
//  "Watch all controllers. If any of them throw an error,
//  send it here!"

@RestControllerAdvice
public class GlobalExceptionHandler {
    // @ExceptionHandler tells Spring exactly WHICH error this method knows how to handle
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationsExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop through all the bouncer's rejection notes and pull out the field name and message
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Return our clean map of errors with a 400 Bad Request status
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

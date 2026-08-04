package com.ajcode.springbootstudentmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> handleIllegalArgumentException(IllegalArgumentException exception)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", HttpStatus.NOT_FOUND.value());
        map.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(RuntimeException exception)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", HttpStatus.CONFLICT.value());
        map.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException(Exception exception)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)
    {
        Map<String,Object> errors = new HashMap<>();
        exception.getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            errors.put(fieldName,error.getDefaultMessage());
        });

        Map<String,Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("message","Validation Error");
        map.put("errors",errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}

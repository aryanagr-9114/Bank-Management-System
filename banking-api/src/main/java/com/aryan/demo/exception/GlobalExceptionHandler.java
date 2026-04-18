package com.aryan.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice tells Spring Boot: 
// "This is the Customer Service Desk. If ANY worker in the bank throws a RuntimeException, forward it here!"
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        // Instead of a giant Java crash, we just hand back a tiny, polite JSON message!
        response.put("error", ex.getMessage());
        return ResponseEntity.status(400).body(response); 
    }
}

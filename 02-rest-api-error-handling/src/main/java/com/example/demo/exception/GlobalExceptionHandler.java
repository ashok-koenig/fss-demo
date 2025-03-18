package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception, WebRequest request){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("Timestamp", LocalDateTime.now());
        errorBody.put("Status", HttpStatus.NOT_FOUND);
        errorBody.put("Error", "Product Not Found");
        errorBody.put("Message", exception.getMessage());
        errorBody.put("Path", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("Timestamp", LocalDateTime.now());
        errorBody.put("Status", HttpStatus.INTERNAL_SERVER_ERROR);
        errorBody.put("Error", "Internal Server Error");
        errorBody.put("Message", exception.getMessage());
        errorBody.put("Path", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }
}

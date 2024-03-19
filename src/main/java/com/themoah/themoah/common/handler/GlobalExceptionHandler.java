package com.themoah.themoah.common.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> generalExceptionHandler(RuntimeException e) {
        return Map.of("error", e.getMessage());
    }
}

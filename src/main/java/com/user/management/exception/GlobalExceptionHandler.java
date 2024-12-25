package com.user.management.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("endpoint", request.getDescription(false).replace("uri=", ""));
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", ex.getStatus());
        body.put("statusCode", ex.getStatus().value());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("endpoint", request.getDescription(false).replace("uri=", ""));
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());
        body.put("message", ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                )));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("endpoint", request.getDescription(false).replace("uri=", ""));
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.CONFLICT);
        body.put("statusCode", HttpStatus.CONFLICT.value());
        body.put("message", "Unique constraint violation: " + extractConstraintName(ex));
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private String extractConstraintName(DataIntegrityViolationException ex) {
        if (ex.getCause() != null && ex.getCause().getCause() != null) {
            return ex.getCause().getCause().getMessage();
        }
        return "Constraint violation";
    }
}


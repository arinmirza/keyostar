package com.example.keyostar.store;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(assignableTypes = KeyValueController.class)
public class KeyValueControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException exception) {
        List<String> messages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).toList();
        // Note: We only report the first violation message.
        return ResponseEntity.badRequest().body(messages.getFirst());
    }
}

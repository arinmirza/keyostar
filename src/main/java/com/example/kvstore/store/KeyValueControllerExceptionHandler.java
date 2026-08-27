package com.example.kvstore.store;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice(assignableTypes = KeyValueController.class)
public class KeyValueControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException exception) {

        List<String> messages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).toList();

        // Note: We only report the first violation message.
        return ResponseEntity.badRequest().body(
                Map.of(
                        "error", "Validation failed",
                        "message", messages.getFirst()
                )
        );
    }
}

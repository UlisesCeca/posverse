package com.ulises.posverse.exceptions.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final JdbcTemplate jdbcTemplate;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationException(final MethodArgumentNotValidException ex) {
        val errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolationException(final ConstraintViolationException ex) {
        val errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", errors));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleUniqueViolation(
            SQLIntegrityConstraintViolationException ex,
            HttpServletRequest request) {

        val detail = ex.getMessage();
        val duplicatedValue = extractDuplicatedValue(detail);
        val constraint = extractConstraintName(detail);
        val table = extractTableName(detail);
        var field = "unknown";

        if (constraint != null && table != null) {
            try {
                field = jdbcTemplate.queryForObject(
                        """
                        SELECT COLUMN_NAME
                        FROM information_schema.STATISTICS
                        WHERE TABLE_SCHEMA = DATABASE()
                          AND TABLE_NAME = ?
                          AND INDEX_NAME = ?
                        LIMIT 1
                        """,
                        String.class,
                        table, constraint
                );
            } catch (Exception ignored) {}
        }

        val error = new HashMap<String, Object>();
        error.put("error", List.of(String.format("Value '%s' already exists", duplicatedValue)));
        error.put("duplicatedField", field);
        error.put("duplicatedValue", duplicatedValue);
        error.put("status", HttpStatus.CONFLICT.value());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    private String extractDuplicatedValue(String message) {
        val start = message.indexOf("Duplicate entry '");
        if (start == -1) return null;
        val firstQuote = message.indexOf("'", start);
        val secondQuote = message.indexOf("'", firstQuote + 1);
        return (firstQuote != -1 && secondQuote != -1)
                ? message.substring(firstQuote + 1, secondQuote)
                : null;
    }

    private String extractConstraintName(String message) {
        val keyIndex = message.indexOf("for key");
        if (keyIndex == -1) return null;
        val key = message.substring(keyIndex).replace("for key", "").replaceAll("[`']", "").trim();
        return key.contains(".") ? key.substring(key.indexOf('.') + 1) : key;
    }

    private String extractTableName(String message) {
        val keyIndex = message.indexOf("for key");
        if (keyIndex == -1) return null;
        val key = message.substring(keyIndex).replace("for key", "").replaceAll("[`']", "").trim();
        return key.contains(".") ? key.substring(0, key.indexOf('.')) : null;
    }
}

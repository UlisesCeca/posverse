package com.ulises.posverse.exceptions.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Handles exceptions thrown when method arguments fail validation.
     * Extracts all field errors, customizes messages especially for enum types,
     * and returns a BAD_REQUEST response with detailed error messages.
     *
     * @param ex the MethodArgumentNotValidException containing validation errors
     * @return ResponseEntity with status 400 and a map containing the list of error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationException(final MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Object rejected = fieldError.getRejectedValue();
                    Class<?> enumType = resolveEnumType(fieldError, ex);

                    if (enumType != null && enumType.isEnum() && rejected != null) {
                        final String allowed = Arrays.stream(enumType.getEnumConstants())
                                .map(Object::toString)
                                .collect(Collectors.joining(", "));

                        return String.format(
                                "Invalid value '%s' for '%s'. Allowed values: %s",
                                rejected, fieldError.getField(), allowed
                        );
                    }

                    return fieldError.getDefaultMessage();
                })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", errors));
    }

    /**
     * Handles exceptions resulting from constraint violations,
     * such as invalid request parameters that fail validation constraints.
     * Returns a BAD_REQUEST response with a list of violation messages.
     *
     * @param ex the ConstraintViolationException containing violation details
     * @return ResponseEntity with status 400 and a map containing the list of violation messages
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolationException(final ConstraintViolationException ex) {
        final List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", errors));
    }

    /**
     * Handles SQL exceptions caused by unique constraint violations,
     * extracting duplicated value, related table, and field info.
     * Returns a CONFLICT response with detailed error information.
     *
     * @param ex the SQLIntegrityConstraintViolationException thrown on unique constraint violation
     * @param request the HttpServletRequest (not used but available for context)
     * @return ResponseEntity with status 409 and a map containing error details
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleUniqueViolation(
            SQLIntegrityConstraintViolationException ex,
            HttpServletRequest request) {

        final String detail = ex.getMessage();
        final String duplicatedValue = extractDuplicatedValue(detail);
        final String constraint = extractConstraintName(detail);
        final String tableName = extractTableName(detail);
        String field = "unknown";

        if (constraint != null && tableName != null) {
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
                        tableName, constraint
                );
            } catch (Exception ignored) {
            }
        }

        final HashMap<String, Object> error = new HashMap<String, Object>();
        error.put("error", List.of(String.format("Value '%s' already exists", duplicatedValue)));
        error.put("duplicatedField", field);
        error.put("duplicatedValue", duplicatedValue);
        error.put("status", HttpStatus.CONFLICT.value());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handles exceptions thrown when an invalid property is referenced in a query or sorting.
     * Returns a BAD_REQUEST response indicating the missing property name.
     *
     * @param ex the PropertyReferenceException indicating invalid property access
     * @return ResponseEntity with status 400 and a map containing the error message
     */
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Map<String, String>> handlePropertyReferenceException(final PropertyReferenceException ex) {
        final String invalidProperty = ex.getPropertyName();
        final String message = String.format("No property '%s' found", invalidProperty);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", message));
    }

    /**
     * Extracts the duplicated value from the SQL exception message.
     *
     * @param message the detailed SQL exception message
     * @return the duplicated value as a String, or null if not found
     */
    private String extractDuplicatedValue(String message) {
        final int start = message.indexOf("Duplicate entry '");
        if (start == -1) {
            return null;
        }
        final int firstQuote = message.indexOf("'", start);
        final int secondQuote = message.indexOf("'", firstQuote + 1);
        return (firstQuote != -1 && secondQuote != -1)
                ? message.substring(firstQuote + 1, secondQuote)
                : null;
    }

    /**
     * Extracts the name of the violated constraint from the SQL exception message.
     *
     * @param message the detailed SQL exception message
     * @return the constraint name as a String, or null if not found
     */
    private String extractConstraintName(String message) {
        final int keyIndex = message.indexOf("for key");
        if (keyIndex == -1) {
            return null;
        }
        final String key = message.substring(keyIndex)
                .replace("for key", "")
                .replaceAll("[`']", "")
                .trim();
        return key.contains(".") ? key.substring(key.indexOf('.') + 1) : key;
    }

    /**
     * Extracts the table name involved in the unique constraint violation from the SQL exception message.
     *
     * @param message the detailed SQL exception message
     * @return the table name as a String, or null if not found
     */
    private String extractTableName(String message) {
        final int keyIndex = message.indexOf("for key");
        if (keyIndex == -1) {
            return null;
        }
        final String key = message.substring(keyIndex)
                .replace("for key", "")
                .replaceAll("[`']", "")
                .trim();
        return key.contains(".") ? key.substring(0, key.indexOf('.')) : null;
    }

    /**
     * Attempts to resolve the enum type of a field based on the FieldError and validation exception.
     * This is used to enhance error messages by listing allowed enum values.
     *
     * @param fieldError the field error object from validation
     * @param ex the MethodArgumentNotValidException containing the target object
     * @return the Class object representing the enum type, or null if not found or not an enum
     */
    private Class<?> resolveEnumType(final FieldError fieldError, final MethodArgumentNotValidException ex) {
        try {
            final Object targetObject = ex.getBindingResult().getTarget();
            if (targetObject == null) {
                return null;
            }

            final Field field = targetObject.getClass().getDeclaredField(fieldError.getField());
            return field.getType();
        } catch (Exception e) {
            return null;
        }
    }

}

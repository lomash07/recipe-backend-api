package com.recipe.app.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        log.error("Resource not found: {}", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {

        log.error("User already exists: {}", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {

        log.error("Access denied: {}", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {

        log.error("Bad credentials: {}", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Invalid username or password",
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(
            UsernameNotFoundException ex, WebRequest request) {

        log.error("Username not found: {}", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception ex, WebRequest request) {

        log.error("Global exception: {}", ex.getMessage(), ex);

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        log.error("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorDetails errorDetails = new ValidationErrorDetails(
                new Date(),
                "Validation Failed",
                request.getDescription(false),
                errors);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Error details class
    public static class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;

        public ErrorDetails(Date timestamp, String message, String details) {
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getDetails() {
            return details;
        }
    }

    // Validation error details class
    public static class ValidationErrorDetails extends ErrorDetails {
        private Map<String, String> errors;

        public ValidationErrorDetails(Date timestamp, String message, String details, Map<String, String> errors) {
            super(timestamp, message, details);
            this.errors = errors;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }
}
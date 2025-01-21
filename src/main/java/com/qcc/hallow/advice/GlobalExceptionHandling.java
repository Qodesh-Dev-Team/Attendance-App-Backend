package com.qcc.hallow.advice;

import com.qcc.hallow.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandling {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
                // Safely map validation errors
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                });

                // Build the error response
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "01",
                                "Error",
                                "Validation failed. Please check the input.",
                                errors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "02",
                                "Error",
                                "Malformed JSON request. Please check the syntax.",
                                null);
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "03",
                                "Error",
                                ex.getMessage(),
                                null);
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(NullPointerException.class)
        public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "04",
                                "Error",
                                "A required parameter was null.",
                                null);
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleUserNotFoundException(EntityNotFoundException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "05",
                                "Error",
                                "User does not exist.",
                                null);
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
                Map<String, String> errors = new HashMap<>();
                String message = "A database integrity violation occurred. Please check the provided data.";
                String constraintName = ex.getMessage();

                if (constraintName.contains("users_tbl_email_key")) {
                        errors.put("email", "The email address is already in use.");
                }

                if (constraintName.contains("users_tbl_username_key")) {
                        errors.put("username", "The username is already taken.");
                }

                if (constraintName.contains("users_tbl_phone_key")) {
                        errors.put("phone", "The phone is already taken.");
                }

                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "06",
                                "Error",
                                message,
                                errors);

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(ExpiredJwtException.class)
        public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "07",
                                "Error",
                                "Token expired at: " + ex.getClaims().getExpiration(),
                                null);
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(JwtException.class)
        public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "08",
                                "Error",
                                ex.getMessage(),
                                null);
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
                ErrorResponse errorResponse = new ErrorResponse(
                                false,
                                "09",
                                "Error",
                                ex.getMessage(),
                                null);
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(errorResponse);
        }

}

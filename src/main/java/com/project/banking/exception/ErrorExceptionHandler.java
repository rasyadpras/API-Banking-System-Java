package com.project.banking.exception;

import com.project.banking.dto.response.format.ErrorExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ErrorExceptionHandler {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ErrorExceptionResponse> responseStatusError(ResponseStatusException e) {
        ErrorExceptionResponse response = ErrorExceptionResponse.builder()
                .statusCode(e.getStatusCode().value())
                .message(HttpStatus.valueOf(e.getStatusCode().value()).getReasonPhrase())
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(e.getStatusCode()).body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorExceptionResponse> constraintViolationError(ConstraintViolationException e) {
        ErrorExceptionResponse response = ErrorExceptionResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorExceptionResponse> dataIntegrityViolationError(DataIntegrityViolationException e) {
        ErrorExceptionResponse.ErrorExceptionResponseBuilder builder = ErrorExceptionResponse.builder();
        HttpStatus httpStatus;

        if (e.getMessage().contains("foreign key constraint")) {
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message(HttpStatus.BAD_REQUEST.getReasonPhrase());
            builder.error("Cannot delete data because other table depend on it");
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e.getMessage().contains("unique constraint")
                || e.getMessage().contains("duplicate entry")
                || e.getMessage().contains("duplicate key value")
                || e.getMessage().contains("already exists")) {
            builder.statusCode(HttpStatus.CONFLICT.value());
            builder.message(HttpStatus.CONFLICT.getReasonPhrase());
            builder.error("Data already exists");
            httpStatus = HttpStatus.CONFLICT;
        } else {
            builder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            builder.message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            builder.error(e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(httpStatus).body(builder.build());
    }
}

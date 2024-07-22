package me.nolanjames.theoldtreeapi.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exception) {
        log.warn(exception.getMessage());

        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityExistsException exception) {
        log.warn(exception.getMessage());

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    var errorField = error.getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(errorField, errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error("Internal error, contact the site owner")
                                .build()
                );
    }
}

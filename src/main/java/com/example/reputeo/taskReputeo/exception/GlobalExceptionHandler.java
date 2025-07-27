package com.example.reputeo.taskReputeo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        log.warn(buildLogJson(HttpStatus.NOT_FOUND, e, request));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<?> handleInvalidData(InvalidData e, HttpServletRequest request) {
        log.warn(buildLogJson(HttpStatus.BAD_REQUEST, e, request));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(KeanuNotAvailableException.class)
    public ResponseEntity<?> handleKeanuNotAvailable(KeanuNotAvailableException e, HttpServletRequest request) {
        log.warn(buildLogJson(HttpStatus.BAD_REQUEST, e, request));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception e, HttpServletRequest request) {
        log.error(buildLogJson(HttpStatus.INTERNAL_SERVER_ERROR, e, request), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal error"));
    }

    private String buildLogJson(HttpStatus status, Exception e, HttpServletRequest request) {
        return String.format(
                "{ \"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\" }",
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
    }
}

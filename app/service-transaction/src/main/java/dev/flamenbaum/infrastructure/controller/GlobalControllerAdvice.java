package dev.flamenbaum.infrastructure.controller;

import dev.flamenbaum.core.domain.OperationType;
import dev.flamenbaum.core.exception.AccountApiException;
import dev.flamenbaum.core.exception.OperationTypeException;
import dev.flamenbaum.core.exception.ResourceNotFoundException;
import dev.flamenbaum.core.exception.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleAccountException(TransactionException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_REQUEST, ex, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_REQUEST, ex, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationTypeException.class)
    public ResponseEntity<Object> handleOperationTypeException(OperationTypeException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_REQUEST, ex, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountApiException.class)
    public ResponseEntity<Object> handleAccountApiException(AccountApiException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_GATEWAY, ex, webRequest), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.NOT_FOUND, ex, webRequest), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        return new ResponseEntity<>(responseBodyBuilder(errors, webRequest), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> responseBodyBuilder(HttpStatus status, RuntimeException ex, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.toString());
        body.put("message", ex.getMessage());
        body.put("path", webRequest.getDescription(false));

        return body;
    }

    private Map<String, Object> responseBodyBuilder(List<String> err, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.toString());
        body.put("message", err);
        body.put("path", webRequest.getDescription(false));

        return body;
    }
}

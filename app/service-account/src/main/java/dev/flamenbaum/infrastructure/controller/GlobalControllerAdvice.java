package dev.flamenbaum.infrastructure.controller;

import dev.flamenbaum.core.exception.AccountException;
import dev.flamenbaum.core.exception.ResourceNotFoundException;
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

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<Object> handleAccountException(AccountException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_REQUEST, ex, webRequest), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_REQUEST, errors, webRequest), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.NOT_FOUND, ex, webRequest), HttpStatus.NOT_FOUND);
    }


    private Map<String, Object> responseBodyBuilder(HttpStatus status, Exception ex, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.toString());
        body.put("message", ex.getMessage());
        body.put("path", webRequest.getDescription(false));

        return body;
    }

    private Map<String, Object> responseBodyBuilder(HttpStatus status, List<String> err, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.toString());
        body.put("message", err);
        body.put("path", webRequest.getDescription(false));

        return body;
    }

}

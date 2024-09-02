package dev.flamenbaum.infrastructure.controller;

import dev.flamenbaum.core.exception.AccountApiException;
import dev.flamenbaum.core.exception.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleAccountException(TransactionException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_REQUEST, ex, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountApiException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(AccountApiException ex, WebRequest webRequest) {
        return new ResponseEntity<>(responseBodyBuilder(HttpStatus.BAD_GATEWAY, ex, webRequest), HttpStatus.BAD_GATEWAY);
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
}

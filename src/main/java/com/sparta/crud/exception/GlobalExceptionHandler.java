package com.sparta.crud.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorResponse> handleHttp(HttpException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getStatus().value(), e.getMessage()));
    }
}

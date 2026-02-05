package com.sparta.crud.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {
    private final HttpStatus status;

    protected HttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}

package com.sparta.crud.exception;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends HttpException {
    protected BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

package com.dddryinside.exeptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIException extends Exception {
    private final HttpStatus httpStatus;
    private final String message;

    public APIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
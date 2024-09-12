package com.exemple.security.playload;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AppApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public AppApiException(String message, HttpStatus status)
    {
       this.message = message;
        this.status = status;
    }

    public AppApiException(String message, HttpStatus status , String message1)
    {
        super(message);
        this.message = message1;
        this.status = status;
    }
}
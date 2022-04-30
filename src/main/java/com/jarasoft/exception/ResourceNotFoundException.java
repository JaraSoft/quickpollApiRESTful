package com.jarasoft.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String messege) {
        super(messege);
    }

    public ResourceNotFoundException(String messege, Throwable throwable) {
        super(messege, throwable);
    }
}

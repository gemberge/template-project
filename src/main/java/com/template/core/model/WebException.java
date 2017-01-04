package com.template.core.model;

import org.springframework.http.HttpStatus;

public class WebException extends Exception {
    private HttpStatus statusCode;
    private String message;

    public WebException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
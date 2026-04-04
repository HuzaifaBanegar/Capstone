package com.huzzi.capstone.AuthService.errorhandler;

public class AuthException extends RuntimeException {
    private final Integer statusCode;

    public AuthException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}

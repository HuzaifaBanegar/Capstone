package com.huzzi.capstone.ProductService.errorhandler;

public class ExternalApiException extends RuntimeException {
    private final Integer statusCode;

    public ExternalApiException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}

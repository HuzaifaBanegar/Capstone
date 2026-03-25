package com.huzzi.capstone.ProductService.errorhandler;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}

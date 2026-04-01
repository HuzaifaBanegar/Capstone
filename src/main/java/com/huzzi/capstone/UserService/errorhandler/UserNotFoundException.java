package com.huzzi.capstone.UserService.errorhandler;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

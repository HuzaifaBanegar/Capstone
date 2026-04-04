package com.huzzi.capstone.AuthService.service;

import com.huzzi.capstone.AuthService.dto.LoginResponseDto;
import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.UserService.model.User;

public interface AuthService {
    public LoginResponseDto auth(Auth userAuth);
    public User signUp(String name, String email, String password);

    public User validateToken(String tokenValue);

    public void logout(String tokenValue);
}

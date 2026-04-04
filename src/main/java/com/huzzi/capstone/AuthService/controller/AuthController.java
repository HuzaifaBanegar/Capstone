package com.huzzi.capstone.AuthService.controller;

import com.huzzi.capstone.AuthService.dto.LoginResponseDto;
import com.huzzi.capstone.AuthService.dto.LogoutRequestDto;
import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.AuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private AuthService authService;

    public AuthController(@Qualifier("dbAuthService") AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDto auth(@RequestBody Auth userAuth) {
        return authService.auth(userAuth);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        authService.logout(logoutRequestDto.getTokenValue());
        return "Logged out successfully";
    }
}

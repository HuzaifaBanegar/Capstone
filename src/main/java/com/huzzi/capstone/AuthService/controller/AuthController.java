package com.huzzi.capstone.AuthService.controller;

import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.AuthService.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String auth(@RequestBody Auth userAuth) {
        return authService.auth(userAuth);
    }
}

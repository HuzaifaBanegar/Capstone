package com.huzzi.capstone.AuthService.controller;

import com.huzzi.capstone.AuthService.dto.AuthResponseDto;
import com.huzzi.capstone.AuthService.dto.SignoutRequestDto;
import com.huzzi.capstone.AuthService.dto.SignoutResponseDto;
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
    public AuthResponseDto login(@RequestBody Auth userAuth) {
        return authService.login(userAuth);
    }

    @PostMapping("/signout")
    public SignoutResponseDto signout(@RequestBody SignoutRequestDto signoutRequestDto) {
        return authService.signout(signoutRequestDto.getAccessToken());
    }
}

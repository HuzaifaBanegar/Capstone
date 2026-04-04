package com.huzzi.capstone.AuthService.service;

import com.huzzi.capstone.AuthService.dto.AuthResponseDto;
import com.huzzi.capstone.AuthService.dto.SignoutResponseDto;
import com.huzzi.capstone.AuthService.modal.Auth;

public interface AuthService {
    public AuthResponseDto login(Auth userAuth);
    public SignoutResponseDto signout(String accessToken);
}

package com.huzzi.capstone.AuthService.dto;

import com.huzzi.capstone.UserService.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    private String accessToken;
    private User user;
}

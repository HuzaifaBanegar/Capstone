package com.huzzi.capstone.UserService.dto;

import com.huzzi.capstone.UserService.model.Role;
import com.huzzi.capstone.UserService.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private Role role;


    public User toUser() {
        User user = new User();
        if (getId() != null) {
            user.setId(getId().longValue());
        }
        user.setEmail(getEmail());
        user.setUsername(getUsername());
        user.setPassword(getPassword());
        user.setRole(getRole());

        return user;
    }
}

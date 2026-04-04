package com.huzzi.capstone.UserService.controller;

import com.huzzi.capstone.UserService.dto.UserDto;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import com.huzzi.capstone.UserService.model.User;
import com.huzzi.capstone.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(@Qualifier("dbUserService") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() throws UserNotFoundException {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.deleteUser(id);
    }

    @PatchMapping("/users/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) throws UserNotFoundException {
        return userService.updateUser(id, userDto);
    }
}

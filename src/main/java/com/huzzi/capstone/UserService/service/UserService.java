package com.huzzi.capstone.UserService.service;

import com.huzzi.capstone.UserService.dto.UserDto;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import com.huzzi.capstone.UserService.model.User;

import java.util.List;

public interface UserService {
    public User getUserById(Long id) throws UserNotFoundException;
    public List<User> getAllUsers() throws UserNotFoundException;
    public User createUser(UserDto userDto);
    public User deleteUser(Long id) throws UserNotFoundException;
    public User updateUser(Long id, UserDto userDto) throws UserNotFoundException;
}

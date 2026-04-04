package com.huzzi.capstone.UserService.service;

import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.UserService.dto.UserDto;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import com.huzzi.capstone.UserService.model.Role;
import com.huzzi.capstone.UserService.model.User;
import com.huzzi.capstone.UserService.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("dbUserService")
public class DbUserService implements UserService {

    UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public  DbUserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        try{
            Optional<User> user = userRepository.findByIdAndIsDeletedFalse(id);
            if(user.isPresent()){
                return user.get();
            }
            throw new UserNotFoundException("User not found");

        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        try{
            List<User> users = userRepository.findAllByIsDeletedFalse();
            if(users.isEmpty()){
                throw new UserNotFoundException("No User found");
            }
            return users;
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public User createUser(UserDto userDto) {
        try{
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(userDto.getRole().equals(Role.USER) || userDto.getRole().equals(Role.ADMIN)){
                user.setRole(userDto.getRole());
            }

            return userRepository.save(user);
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public User deleteUser(Long id) throws UserNotFoundException {
        try{
            Optional<User> user = userRepository.findByIdAndIsDeletedFalse(id);
            if(user.isPresent()){
                User existingUser = user.get();
                existingUser.setIsDeleted(true);
                existingUser.setUpdated_at(new Date());
                return userRepository.save(existingUser);
            }
            throw new UserNotFoundException("User not found");
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public User updateUser(Long id, UserDto userDto) throws UserNotFoundException {
        try{
            Optional<User> user = userRepository.findByIdAndIsDeletedFalse(id);
            if(user.isPresent()){
                User existingUser = user.get();
                existingUser.setEmail(userDto.getEmail());
                existingUser.setUsername(userDto.getUsername());
                if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
                    existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                }
                existingUser.setUpdated_at(new Date());
                if(userDto.getRole().equals(Role.USER) || userDto.getRole().equals(Role.ADMIN)){
                    existingUser.setRole(userDto.getRole());
                }

                return userRepository.save(existingUser);
            }
            throw new UserNotFoundException("User not found");
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }
    

}

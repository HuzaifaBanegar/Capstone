package com.huzzi.capstone.UserService.service;

import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.UserService.dto.UserDto;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import com.huzzi.capstone.UserService.model.User;
import com.huzzi.capstone.UserService.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("dbUserService")
public class DbUserService implements UserService {

    UserRepository userRepository;
    public  DbUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setAddress(userDto.getAddress());
            user.setPhone(userDto.getPhone());

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
                existingUser.setName(userDto.getName());
                existingUser.setEmail(userDto.getEmail());
                existingUser.setUsername(userDto.getUsername());
                existingUser.setPassword(userDto.getPassword());
                existingUser.setAddress(userDto.getAddress());
                existingUser.setPhone(userDto.getPhone());
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
    

}

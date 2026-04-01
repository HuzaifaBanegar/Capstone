package com.huzzi.capstone.UserService.service;

import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.UserService.dto.UserDto;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import com.huzzi.capstone.UserService.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeUserApi")
public class FakeUserApiService implements UserService {
    private final RestTemplate restTemplate;

    public FakeUserApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        try {
            ResponseEntity<UserDto> response = restTemplate.getForEntity(
                    "https://fakestoreapi.com/users/" + id,
                    UserDto.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().toUser();
            }
            throw new UserNotFoundException("User not found");
        } catch (RestClientResponseException e) {
            throw new ExternalApiException(
                    "Fake Store API get user failed with status " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        try {
            UserDto[] users = restTemplate.getForObject("https://fakestoreapi.com/users", UserDto[].class);
            List<User> userList = new ArrayList<>();

            if (users != null) {
                for (UserDto user : users) {
                    userList.add(user.toUser());
                }
            }

            if (!userList.isEmpty()) {
                return userList;
            }
            throw new UserNotFoundException("Users not found");
        } catch (RestClientResponseException e) {
            throw new ExternalApiException(
                    "Fake Store API get users failed with status " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public User createUser(UserDto userDto) {
        try {
            UserDto response = restTemplate.postForObject(
                    "https://fakestoreapi.com/users",
                    userDto,
                    UserDto.class
            );

            if (response != null) {
                return response.toUser();
            }
            throw new ExternalApiException("Fake Store API create user returned empty response", 502);
        } catch (RestClientResponseException e) {
            throw new ExternalApiException(
                    "Fake Store API create user failed with status " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public User deleteUser(Long id) throws UserNotFoundException {
        try {
            ResponseEntity<UserDto> response = restTemplate.exchange(
                    "https://fakestoreapi.com/users/" + id,
                    HttpMethod.DELETE,
                    null,
                    UserDto.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().toUser();
            }
            throw new UserNotFoundException("User not found");
        } catch (RestClientResponseException e) {
            throw new ExternalApiException(
                    "Fake Store API delete user failed with status " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public User updateUser(Long id, UserDto userDto) throws UserNotFoundException {
        userDto.setId(id.intValue());
        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto);

        try {
            ResponseEntity<UserDto> response = restTemplate.exchange(
                    "https://fakestoreapi.com/users/" + id,
                    HttpMethod.PUT,
                    requestEntity,
                    UserDto.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().toUser();
            }
            throw new UserNotFoundException("User not found");
        } catch (RestClientResponseException e) {
            throw new ExternalApiException(
                    "Fake Store API update user failed with status " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }
}

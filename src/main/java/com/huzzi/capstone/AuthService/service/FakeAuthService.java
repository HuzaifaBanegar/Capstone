package com.huzzi.capstone.AuthService.service;

import com.huzzi.capstone.AuthService.dto.LoginResponseDto;
import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import com.huzzi.capstone.UserService.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service("fakeAuthService")
public class FakeAuthService implements AuthService{
    private RestTemplate restTemplate;

    public FakeAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public LoginResponseDto auth(Auth userAuth) {
        try {
            String token =  restTemplate.postForObject("https://fakestoreapi.com/auth/login", userAuth, String.class );
            if(token != null){
                LoginResponseDto responseDto = new LoginResponseDto();
                responseDto.setAccessToken(token);
                responseDto.setUser(new User());
                return responseDto;
            }
            throw new UserNotFoundException("User not found");
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Fake Store API get user failed with status " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }

    }

    @Override
    public User signUp(String name, String email, String password) {
        System.out.println("No implementation available");
        return null;
    }

    @Override
    public User validateToken(String tokenValue) {
        System.out.println("No implementation available");
        return null;
    }

    @Override
    public void logout(String tokenValue) {
        System.out.println("No implementation available");
    }
}

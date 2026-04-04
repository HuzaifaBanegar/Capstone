package com.huzzi.capstone.AuthService.service;

import com.huzzi.capstone.AuthService.dto.AuthResponseDto;
import com.huzzi.capstone.AuthService.dto.SignoutResponseDto;
import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
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
    public AuthResponseDto login(Auth userAuth) {
        try {
            AuthResponseDto response =  restTemplate.postForObject("https://fakestoreapi.com/auth/login", userAuth, AuthResponseDto.class );
            if(response != null){
                return response;
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
    public SignoutResponseDto signout(String accessToken) {
        SignoutResponseDto responseDto = new SignoutResponseDto();
        responseDto.setMessage("Fake auth signout is handled locally by the client");
        return responseDto;
    }
}

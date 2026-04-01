package com.huzzi.capstone.AuthService.service;

import com.huzzi.capstone.AuthService.modal.Auth;
import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import org.springframework.http.ResponseEntity;
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
    public String auth(Auth userAuth) {
        try {

            String response =  restTemplate.postForObject("https://fakestoreapi.com/auth/", userAuth, String.class );

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
}

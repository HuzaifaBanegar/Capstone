package com.huzzi.capstone.ProductService.service;

import com.huzzi.capstone.ProductService.model.Product;
import org.springframework.web.client.RestTemplate;

public class FakeApiService implements ProductService{
    private RestTemplate restTemplate;
    public FakeApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getProductById(Long id) {
        return null;
    }
}

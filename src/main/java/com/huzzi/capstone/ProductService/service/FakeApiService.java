package com.huzzi.capstone.ProductService.service;

import com.huzzi.capstone.ProductService.dto.FakeStoreDto;
import com.huzzi.capstone.ProductService.dto.ProductCreatedDTO;
import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.ProductService.errorhandler.ProductNotFoundException;
import com.huzzi.capstone.ProductService.model.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service("fakeApi")
public class FakeApiService implements ProductService{
    private RestTemplate restTemplate;
    public FakeApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        ResponseEntity<FakeStoreDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreDto.class);
        System.out.println("Response for id "+id+" is: "+ response.getBody());
        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            return response.getBody().toProduct();
        }
        throw new ProductNotFoundException("Product not found");
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        FakeStoreDto[] products = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreDto[].class);
        List<Product> productList = new ArrayList<>();
        for (FakeStoreDto product : products) {
            productList.add(product.toProduct());
        }

        if(productList != null && productList.size() > 0){
            return productList;
        }

        throw new ProductNotFoundException("Products not found");

    }

    @Override
    public ProductCreatedDTO createProduct(String title, String description, Float price, String image, String category) {
        ProductCreatedDTO productCreatedDTO = new ProductCreatedDTO();
        productCreatedDTO.setTitle(title);
        productCreatedDTO.setDescription(description);
        productCreatedDTO.setPrice(price);

        FakeStoreDto response = restTemplate.postForObject("https://fakestoreapi.com/products", productCreatedDTO, FakeStoreDto.class);

        productCreatedDTO.setId(response.toProduct().getId());

        return productCreatedDTO;

    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        try {
            ResponseEntity<FakeStoreDto> response = restTemplate.exchange(
                    "https://fakestoreapi.com/products/" + id,
                    HttpMethod.DELETE,
                    null,
                    FakeStoreDto.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().toProduct();
            }
        } catch (RestClientResponseException e) {
            throw new ExternalApiException(
                    "Fake Store API delete failed with status " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }

        throw new ProductNotFoundException("Product not found");
    }


}

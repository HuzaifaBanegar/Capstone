package com.huzzi.capstone.ProductService.controller;

import com.huzzi.capstone.ProductService.dto.ProductCreatedDTO;
import com.huzzi.capstone.ProductService.errorhandler.ProductNotFoundException;
import com.huzzi.capstone.ProductService.model.Product;
import com.huzzi.capstone.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(@Qualifier("fakeApi") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() throws ProductNotFoundException {
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public ProductCreatedDTO createProduct(String title, String description, Float price, String image, String category) {
       return productService.createProduct(title, description, price, image, category);
    }
}

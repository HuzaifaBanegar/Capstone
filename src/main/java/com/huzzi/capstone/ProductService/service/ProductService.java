package com.huzzi.capstone.ProductService.service;

import com.huzzi.capstone.ProductService.dto.ProductCreatedDTO;
import com.huzzi.capstone.ProductService.errorhandler.ProductNotFoundException;
import com.huzzi.capstone.ProductService.model.Product;

import java.util.List;

public interface ProductService {
    public Product getProductById(Long id) throws ProductNotFoundException;
    public List<Product> getAllProducts() throws ProductNotFoundException;
    public ProductCreatedDTO createProduct(String title, String description, Float price, String image, String category);
    public Product deleteProduct(Long id) throws ProductNotFoundException;
}

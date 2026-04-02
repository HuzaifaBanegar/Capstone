package com.huzzi.capstone.ProductService.controller;

import com.huzzi.capstone.ProductService.dto.FakeStoreDto;
import com.huzzi.capstone.ProductService.dto.ProductCreatedDTO;
import com.huzzi.capstone.ProductService.errorhandler.ProductNotFoundException;
import com.huzzi.capstone.ProductService.model.Product;
import com.huzzi.capstone.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(@Qualifier("dbproductservice") ProductService productService){
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
    public ProductCreatedDTO createProduct(@RequestBody FakeStoreDto fakeStoreProduct) {
       return productService.createProduct(
               fakeStoreProduct.getTitle(),
               fakeStoreProduct.getDescription(),
               fakeStoreProduct.getPrice(),
               fakeStoreProduct.getImage(),
               fakeStoreProduct.getCategory()
       );
    }

    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.deleteProduct(id);
    }

    @PatchMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody FakeStoreDto fakeStoreProduct) throws ProductNotFoundException {
        return productService.updateProduct(id, fakeStoreProduct.getTitle(), fakeStoreProduct.getDescription(), fakeStoreProduct.getPrice(), fakeStoreProduct.getImage(), fakeStoreProduct.getCategory());
    }
}

package com.huzzi.capstone.ProductService.service;

import com.huzzi.capstone.ProductService.dto.ProductCreatedDTO;
import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.ProductService.errorhandler.ProductNotFoundException;
import com.huzzi.capstone.ProductService.model.Category;
import com.huzzi.capstone.ProductService.model.Product;
import com.huzzi.capstone.ProductService.repository.CategoryRepository;
import com.huzzi.capstone.ProductService.repository.ProductRespository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("dbproductservice")
public class DbProductService implements ProductService{
    private ProductRespository productRespository;
    private CategoryRepository categoryRepository;
    public DbProductService(ProductRespository productRespository, CategoryRepository categoryRepository) {
        this.productRespository = productRespository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        try {
            Optional<Product> product = productRespository.findByIdAndIsDeletedFalse(id);
            if (product.isPresent()) {
                return product.get();
            }
            throw new ProductNotFoundException("Product not found");
        }catch (RestClientResponseException e){
                throw new ExternalApiException(
                        "Something went wrong " + e.getStatusCode().value(),
                        e.getStatusCode().value()
                );
            }
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        try {
            List<Product> products = productRespository.findAllByIsDeletedFalse();
            if (products.size() > 0) {
                return products;
            }
            throw new ProductNotFoundException("Products not found");
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public ProductCreatedDTO createProduct(String title, String description, Float price, String image, String category) {

        try{
            Product product = new Product();

            product.setTitle(title);
            product.setDescription(description);
            product.setPrice(price);
            product.setImage(image);

            Category categoryObj = categoryRepository.findByName(category)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(category);
                        return newCategory;
                    });
            product.setCategory(categoryObj);

           Product createdProduct = productRespository.save(product);

           ProductCreatedDTO productCreatedDTO = new ProductCreatedDTO();
           productCreatedDTO.setId(createdProduct.getId());
           productCreatedDTO.setTitle(createdProduct.getTitle());
           productCreatedDTO.setDescription(createdProduct.getDescription());
           productCreatedDTO.setPrice(createdProduct.getPrice());

           return productCreatedDTO;
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        try {
            Optional<Product> product = productRespository.findByIdAndIsDeletedFalse(id);
            if (product.isPresent()) {
                Product existingProduct = product.get();
                existingProduct.setIsDeleted(true);
                existingProduct.setUpdated_at(new Date());
                return productRespository.save(existingProduct);
            }
            throw new ProductNotFoundException("Product not found");
        }catch (RestClientResponseException e){
            throw new ExternalApiException(
                    "Something went wrong " + e.getStatusCode().value(),
                    e.getStatusCode().value()
            );
        }
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Float price, String image, String category) throws ProductNotFoundException {
       try {
           Optional<Product> product = productRespository.findByIdAndIsDeletedFalse(id);
           if (product.isPresent()) {
               Product updatedProduct = product.get();
               updatedProduct.setTitle(title);
               updatedProduct.setDescription(description);
               updatedProduct.setPrice(price);
               updatedProduct.setImage(image);
               updatedProduct.setUpdated_at(new Date());
               updatedProduct.setCategory(categoryRepository.findByName(category)
                       .orElseGet(() -> {
                           Category newCategory = new Category();
                           newCategory.setName(category);
                           return newCategory;
                       }));
               return productRespository.save(updatedProduct);
           }
           throw new ProductNotFoundException("Product not found");
       }catch (RestClientResponseException e){
           throw new ExternalApiException(
                   "Something went wrong " + e.getStatusCode().value(),
                   e.getStatusCode().value()
           );
       }
    }
}

package com.huzzi.capstone.ProductService.repository;

import com.huzzi.capstone.ProductService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRespository extends JpaRepository<Product,Long> {
    Product save(Product product);

    Product findById(long id);

    List<Product> findAll();

    void deleteById(long id);

    Optional<Product> findByIdAndIsDeletedFalse(Long id);

    List<Product> findAllByIsDeletedFalse();


}

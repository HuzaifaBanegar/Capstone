package com.huzzi.capstone.ProductService.dto;

import com.huzzi.capstone.ProductService.model.Category;
import com.huzzi.capstone.ProductService.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDto {
    private Integer id;
    private String title;
    private String description;
    private Float price;
    private String image;
    private String category;
    public Product toProduct(){
        Product product = new Product();
        product.setId((long) getId());
        product.setTitle(getTitle());
        product.setDescription(getDescription());
        product.setPrice(getPrice());
        product.setImage(getImage());

        Category category = new Category();
        category.setName(getCategory());

        product.setCategory(category);
        return product;
    }
}

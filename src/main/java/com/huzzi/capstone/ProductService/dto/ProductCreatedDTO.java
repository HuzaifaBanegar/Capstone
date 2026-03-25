package com.huzzi.capstone.ProductService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreatedDTO {
    private Long id;
    private String title;
    private String description;
    private Float price;
}

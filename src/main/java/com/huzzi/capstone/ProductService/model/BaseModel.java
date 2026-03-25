package com.huzzi.capstone.ProductService.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class BaseModel {
    private Long id;
    private Date created_at ;
    private Date updated_at ;
    private Boolean isDeleted = false;
}

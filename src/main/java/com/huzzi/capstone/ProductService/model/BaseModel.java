package com.huzzi.capstone.ProductService.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class BaseModel {
    Long id;
    Date created_at;
    Date updated_at;
    Boolean isDeleted;
}

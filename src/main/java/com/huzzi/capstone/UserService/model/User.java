package com.huzzi.capstone.UserService.model;

import com.huzzi.capstone.ProductService.model.BaseModel;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String email;
    private String username;
    private String password;
    private String name;
    private String address;
    private String phone;
}

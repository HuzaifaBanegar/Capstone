package com.huzzi.capstone.UserService.model;

import com.huzzi.capstone.ProductService.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String email;
    private String username;
    private String password;
    private Role role;
}

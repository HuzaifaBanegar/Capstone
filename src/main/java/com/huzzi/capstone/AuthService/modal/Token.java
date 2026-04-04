package com.huzzi.capstone.AuthService.modal;

import com.huzzi.capstone.ProductService.model.BaseModel;
import com.huzzi.capstone.UserService.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String value;

    @ManyToOne
    private User user;

    private LocalDateTime expiryDate;
}

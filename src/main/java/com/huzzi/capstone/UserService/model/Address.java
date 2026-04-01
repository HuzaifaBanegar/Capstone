package com.huzzi.capstone.UserService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private Geolocation geolocation;
    private String city;
    private String street;
    private Integer number;
    private String zipcode;
}

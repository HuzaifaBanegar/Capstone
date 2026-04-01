package com.huzzi.capstone.UserService.dto;

import com.huzzi.capstone.UserService.model.Address;
import com.huzzi.capstone.UserService.model.Geolocation;
import com.huzzi.capstone.UserService.model.Name;
import com.huzzi.capstone.UserService.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private NameDto name;
    private AddressDto address;
    private String phone;

    public User toUser() {
        User user = new User();
        if (getId() != null) {
            user.setId(getId().longValue());
        }
        user.setEmail(getEmail());
        user.setUsername(getUsername());
        user.setPassword(getPassword());
        user.setPhone(getPhone());

        if (getName() != null) {
            Name userName = new Name();
            userName.setFirstname(getName().getFirstname());
            userName.setLastname(getName().getLastname());
            user.setName(userName);
        }

        if (getAddress() != null) {
            Address userAddress = new Address();
            userAddress.setCity(getAddress().getCity());
            userAddress.setStreet(getAddress().getStreet());
            userAddress.setNumber(getAddress().getNumber());
            userAddress.setZipcode(getAddress().getZipcode());

            if (getAddress().getGeolocation() != null) {
                Geolocation geolocation = new Geolocation();
                geolocation.setLat(getAddress().getGeolocation().getLat());
                geolocation.setLng(getAddress().getGeolocation().getLng());
                userAddress.setGeolocation(geolocation);
            }

            user.setAddress(userAddress);
        }

        return user;
    }

    @Getter
    @Setter
    public static class NameDto {
        private String firstname;
        private String lastname;
    }

    @Getter
    @Setter
    public static class AddressDto {
        private GeolocationDto geolocation;
        private String city;
        private String street;
        private Integer number;
        private String zipcode;
    }

    @Getter
    @Setter
    public static class GeolocationDto {
        private String lat;
        private String lng;
    }
}

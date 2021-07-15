package com.example.twelve.Model;

import com.example.twelve.Prevalent.Prevalent;

public class Users {
    private String name, phone, email, password, username, profile_image, delieveryphone, address;


    public Users(){

    }

    public Users(String name, String phone, String email, String password, String username, String profile_image, String delieveryphone, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profile_image = profile_image;
        this.delieveryphone = delieveryphone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDelieveryphone() {
        return delieveryphone;
    }

    public void setDelieveryphone(String delieveryphone) {
        this.delieveryphone = delieveryphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

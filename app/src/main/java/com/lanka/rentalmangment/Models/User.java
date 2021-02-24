package com.lanka.rentalmangment.Models;


import com.google.gson.annotations.JsonAdapter;
import com.lanka.rentalmangment.utils.DateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private Long userId;
    private String email, username, password, name, photoURL, address, contactNumber;
    private String role;

    @JsonAdapter(DateConverter.class)
    private LocalDate createdAt;
    @JsonAdapter(DateConverter.class)
    private LocalDate updatedAt;

    private Role[] roles;

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(String email, String username, String password, String name, String photoURL, String role, String address, String contact) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.photoURL = photoURL;
        this.role = role;
        this.address=address;
        this.contactNumber=contact;
    }

    public User(String name, String email, String username, String password, String photoURL, String role) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.name=name;
        this.photoURL=photoURL;
        this.role=role;

    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
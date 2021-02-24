package com.lanka.rentalmangment.DTO.Responses;


import java.util.List;

public class LoginResponse {
    private String accessToken;
    private String id;
    private String username;
    private String email;
    private List<String> roles;
    private String imageUrl;
    private String contactNumber, address, name;


    public LoginResponse() {
    }

    public LoginResponse(String accessToken, String id, String username, String email, List<String> roles, String imageUrl, String contactNumber, String address, String name) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.imageUrl=imageUrl;
        this.contactNumber=contactNumber;
        this.address=address;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginResponse(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return accessToken;
    }

    public void setToken(String token) {
        this.accessToken = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

//    public String getTokenExpireTime() {
//        return tokenExpireTime;
//    }

//    public void setTokenExpireTime(String tokenExpireTime) {
//        this.tokenExpireTime = tokenExpireTime;
//    }


    public String getContact() {
        return contactNumber;
    }

    public void setContact(String contact) {
        this.contactNumber = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

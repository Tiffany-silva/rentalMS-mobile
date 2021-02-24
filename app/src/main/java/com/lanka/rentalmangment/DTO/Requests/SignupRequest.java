package com.lanka.rentalmangment.DTO.Requests;
public class SignupRequest {

    private String username;
    private String email;
    private String role;
    private String password;
    private String name;

    public SignupRequest(String username, String email, String role, String password, String name) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

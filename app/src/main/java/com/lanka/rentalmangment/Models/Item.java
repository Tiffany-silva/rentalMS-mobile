package com.lanka.rentalmangment.Models;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.lanka.rentalmangment.utils.DateConverter;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private long id;
    private String itemName;
    private String description;
    private Integer quantity;
    private Double price;
    private User user;
    private Category category;
    private String image;

    @JsonAdapter(DateConverter.class)
    private LocalDate createdAt;
    @JsonAdapter(DateConverter.class)
    private LocalDate updatedAt;

    public Item(String itemName, String description, double price) {
    }

    public Item(){}
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Item(long id, String itemName, String description, Integer quantity, Double price, Category category, User user, String image) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.description=description;
        this.user=user;
        this.image=image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String url) {
        this.image = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
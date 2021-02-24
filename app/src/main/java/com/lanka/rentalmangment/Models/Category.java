package com.lanka.rentalmangment.Models;


import com.google.gson.annotations.JsonAdapter;
import com.lanka.rentalmangment.utils.DateConverter;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    private long id;
    private String categoryName;

    @JsonAdapter(DateConverter.class)
    private LocalDate createdAt;
    @JsonAdapter(DateConverter.class)
    private LocalDate updatedAt;

    public Category() { }


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

    public Category(long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
    public Category(String categoryName) { this.categoryName = categoryName; }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

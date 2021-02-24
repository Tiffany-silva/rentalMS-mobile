package com.lanka.rentalmangment.DTO.Requests;

public class ItemRequest {

    private String description;

    public ItemRequest(String description, Integer quantity, Double price) {
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    private Integer quantity;

    private Double price;

    public ItemRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
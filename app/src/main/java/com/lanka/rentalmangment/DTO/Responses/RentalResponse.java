package com.lanka.rentalmangment.DTO.Responses;

import com.google.gson.annotations.JsonAdapter;
import com.lanka.rentalmangment.Models.EStatus;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.utils.DateConverter;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalResponse {
    private Long id;
    private String rentalDate;
    private String returnDate;
    private Double totalPrice;
    private EStatus status;
    private User user;
    private Item item;
    @JsonAdapter(DateConverter.class)
    private LocalDate createdAt;
    @JsonAdapter(DateConverter.class)
    private LocalDate updatedAt;

    public RentalResponse(Long id, @NotNull String rentalDate, @NotNull String returnDate,
                  @NotNull Double totalPrice, User user, Item item, EStatus status) {
        this.id = id;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.user = user;
        this.item = item;
        this.status=status;

    }
    public RentalResponse(){}

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

    public EStatus getStatus() { return status; }

    public void setStatus(EStatus status) { this.status = status; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Double getTotalPrice() { return totalPrice; }

    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Item getItem() { return item; }

    public void setItem(Item item) { this.item = item; }
}
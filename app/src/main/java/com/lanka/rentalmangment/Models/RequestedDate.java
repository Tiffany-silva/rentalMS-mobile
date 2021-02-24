package com.lanka.rentalmangment.Models;

import com.google.gson.annotations.JsonAdapter;
import com.lanka.rentalmangment.utils.DateConverter;
import com.lanka.rentalmangment.utils.DateConverterRentalDate;

import java.util.Date;

public class RequestedDate {
    @JsonAdapter(DateConverterRentalDate.class)
    private Date rentalDate;

    @JsonAdapter(DateConverterRentalDate.class)
    private Date returnDate;

    public RequestedDate() {
    }

    public RequestedDate(Date rentalDate, Date returnDate) {
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

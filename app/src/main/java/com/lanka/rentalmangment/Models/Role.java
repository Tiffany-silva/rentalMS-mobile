package com.lanka.rentalmangment.Models;

import com.google.gson.annotations.JsonAdapter;
import com.lanka.rentalmangment.utils.DateConverter;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {
    private Integer id;
    private ERole type;
    @JsonAdapter(DateConverter.class)
    private LocalDate createdAt;
    @JsonAdapter(DateConverter.class)
    private LocalDate updatedAt;
    public Role() {
    }

    public Role(ERole type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getType() {
        return type;
    }

    public void setType(ERole name) {
        this.type = name;
    }
}


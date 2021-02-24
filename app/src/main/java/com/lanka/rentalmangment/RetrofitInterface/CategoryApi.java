package com.lanka.rentalmangment.RetrofitInterface;

import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.Models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CategoryApi {

    @GET("api/categories")
    Call<List<Category>> getAllCategory(@Header("Authorization") String token);

    @POST("api/categories/add")
    Call<Category> saveCategory(@Body Category category, @Header("Authorization") String token);
}

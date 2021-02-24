package com.lanka.rentalmangment.RetrofitInterface;


import com.lanka.rentalmangment.DTO.Requests.ItemRequest;
import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/api/users")
    Call<List<User>> getAllUsers(@Header("Authorization") String token);

    @PUT("/api/users/{userId}/updateName")
    Call<User> updateName(@Path("userId") long userId, @Body User user, @Header("Authorization") String token);

    @DELETE("/api/users/{userId}")
    Call<User> deleteUser(@Path("userId") long userId,@Header("Authorization") String token);

    @GET("/users/userId/{userId}")
    Call <User> getUserById(@Path("userId") long userId, @Header("Authorization") String token);

}
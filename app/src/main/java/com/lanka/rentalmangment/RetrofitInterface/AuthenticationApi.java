package com.lanka.rentalmangment.RetrofitInterface;


import com.lanka.rentalmangment.DTO.Requests.LoginRequest;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import java.io.File;
import com.lanka.rentalmangment.Models.User;

import org.w3c.dom.Entity;

import okhttp3.MultipartBody;
import retrofit.http.Header;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthenticationApi {

    @POST("/api/auth/login")
    Call <LoginResponse> authenticateUser(@Body LoginRequest loginRequest);

    @POST("/api/auth/signup")
    Call<MessageResponse> registerUser(@Body User user);
}

package com.lanka.rentalmangment.RetrofitAPIService;

import com.lanka.rentalmangment.CallBacks.CustomizeCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Requests.LoginRequest;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.DTO.Responses.MessageResponse;

import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.AuthenticationApi;


import retrofit2.Call;

public class AuthenticationService {
    AuthenticationApi authenticationApi;
    Connection connection;
    public AuthenticationService() {
        this.authenticationApi = connection.getRetrofitClientInstance().create(AuthenticationApi.class);
    }

    public Call<LoginResponse> login(LoginRequest request, ResponseCallback callback) {
        Call<LoginResponse> call = authenticationApi.authenticateUser(request);
        return call;
    }

    public void register(User user, ResponseCallback callback) {
        Call<MessageResponse> call = authenticationApi.registerUser(user);
        call.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }
}

package com.lanka.rentalmangment.RetrofitAPIService;

import com.lanka.rentalmangment.CallBacks.CustomizeCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.UserApi;

import java.util.List;

import retrofit2.Call;

public class UserService {
    UserApi userAPI;
    Connection connection;

    public UserService() {
        this.userAPI = connection.getRetrofitClientInstance().create(UserApi.class);
    }

    public void getAllUsers(String token, ResponseCallback callback) {
        Call<List<User>> call = userAPI.getAllUsers(token);
        call.enqueue(new CustomizeCallback<List<User>>(callback));
    }

    public void getUserById(Long userId, String token, ResponseCallback callback) {
        Call<User> call = userAPI.getUserById(userId, token);
        call.enqueue(new CustomizeCallback <User> (callback));
    }
}

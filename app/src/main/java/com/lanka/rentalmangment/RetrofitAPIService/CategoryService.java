package com.lanka.rentalmangment.RetrofitAPIService;



import com.lanka.rentalmangment.CallBacks.CustomizeCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.Models.Category;
import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.CategoryApi;

import java.util.List;

import retrofit2.Call;

public class CategoryService {
    CategoryApi categoryApi;
    Connection connection;

    public CategoryService() {
        this.categoryApi = connection.getRetrofitClientInstance().create(CategoryApi.class);
    }

    public void getAllCategory(String token, ResponseCallback callback) {
        Call<List<Category>> call = categoryApi.getAllCategory(token);
        call.enqueue(new CustomizeCallback<List<Category>>(callback));
    }
    public void saveCategory(Category category, String token, ResponseCallback callback)
    {
        Call<Category> call= categoryApi.saveCategory(category,token);
        call.enqueue(new CustomizeCallback<Category>(callback));
    }
}
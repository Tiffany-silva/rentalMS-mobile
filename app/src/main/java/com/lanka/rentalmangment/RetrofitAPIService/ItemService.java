package com.lanka.rentalmangment.RetrofitAPIService;

import com.lanka.rentalmangment.CallBacks.CustomizeCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.ItemApi;

import java.util.List;

import retrofit2.Call;

public class ItemService {
    ItemApi itemApi;
    Connection connection;

    public ItemService() {
        this.itemApi = connection.getRetrofitClientInstance().create(ItemApi.class);
    }

     public void getAllItemsByCategory(long categoryId, String token, ResponseCallback callback) {
        Call<List<Item>>call = itemApi.getAllItemsByCategory(categoryId, token);
         call.enqueue(new CustomizeCallback<List<Item>>(callback));
    }

    public void getAllItemByUserAndCategory(long categoryId, long userId, String token, ResponseCallback callback) {
        Call<List<Item>>call = itemApi.getAllItemsByUserIdAndCategoryId(categoryId, userId, token);
        call.enqueue(new CustomizeCallback<List<Item>>(callback));
    }

    public void getAllItemsByUserId(long userId, String token, ResponseCallback callback) {
        Call<List<Item>> call = itemApi.getAllItemsByUserId(userId, token);
        call.enqueue(new CustomizeCallback<List<Item>>(callback));
    }
    public void getAllByItemName(String itemName, String token, ResponseCallback callback) {
        Call<List<Item>> call = itemApi.getAllByItemName(itemName, token);
        call.enqueue(new CustomizeCallback<List<Item>>(callback));
    }
    public void getAllItems(String token, ResponseCallback callback) {
        Call<List<Item>> call = itemApi.getAllItems(token);
        call.enqueue(new CustomizeCallback<List<Item>>(callback));
    }
    public void getItembyId(long itemId,String token, ResponseCallback callback) {
        Call call = itemApi.getItembyId(itemId,token);
        call.enqueue(new CustomizeCallback <Item> (callback));
    }
    public void createItem(long userId, long categoryId,  Item item,String token, ResponseCallback callback) {
        Call<Item>call = itemApi.createItem(userId,categoryId,item,token);
        call.enqueue(new CustomizeCallback<Item> (callback));
    }
    public void updateItemQuantity(long categoryId, long userId, Item item, String token, ResponseCallback callback) {
        Call<Item> call = itemApi.updateItemQuantity(categoryId, userId, item, token);
        call.enqueue(new CustomizeCallback<Item>(callback));
    }
    public void updateItem(long userId,long itemId,Item item, String token, ResponseCallback callback) {
        Call<Item> call = itemApi.updateItem(userId,itemId,item, token);
        call.enqueue(new CustomizeCallback<Item>(callback));
    }
    public void updateItemPrice(long userId, long itemId,Item item, String token, ResponseCallback callback) {
        Call<Item>call = itemApi.updateItemPrice(userId,itemId,item,token);
        call.enqueue(new CustomizeCallback<Item>(callback));
    }
//    public void deleteItem(long userId,long itemId,Item item,String token, ResponseCallback callback) {
//        Call<Item> call = itemApi.deleteItem(userId,itemId,item, token);
//        call.enqueue(new CustomizeCallback<Item>(callback));
//    }


}

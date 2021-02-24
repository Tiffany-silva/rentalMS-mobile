package com.lanka.rentalmangment.RetrofitInterface;


import com.lanka.rentalmangment.Models.Item;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.*;

public interface ItemApi {

    @GET("/api/categories/{categoryId}/items")
    Call<List<Item>> getAllItemsByCategory(@Path("categoryId") long categoryId, @Header("Authorization") String token);

    @GET("/api/users/{userId}/items")
    Call<List<Item>> getAllItemsByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    @GET("/api/users/{userId}/categories/{categoryId}/items")
    Call<List<Item>> getAllItemsByUserIdAndCategoryId(@Path("categoryId") long categoryId, @Path("userId") long userId, @Header("Authorization") String token);

    @GET("/api/items/itemName{itemName}")
    Call<List<Item>> getAllByItemName(@Path("itemName") String itemName, @Header("Authorization") String token);

    @GET("/api/items")
    Call<List<Item>> getAllItems(@Header("Authorization") String token);

    @GET("/api/items/itemId/{itemId}")
    Call <Item> getItembyId(@Path("itemId") long itemId, @Header("Authorization") String token);

    @POST("/api/users/{userId}/categories/{categoryId}/items")
    Call<Item> createItem(@Path("userId") long userId, @Path("categoryId") long categoryId, @Body Item item, @Header("Authorization") String token);

    @PUT("/api/categories/{categoryId}/items/{itemId}/updateQuantity")
    Call<Item> updateItemQuantity(@Path("categoryId") long categoryId, @Path("userId") long userId, @Body Item item, @Header("Authorization") String token);

    @PUT("/api/categories/{categoryId}/items/{itemId}/update")
    Call<Item> updateItem(@Path("categoryId") long categoryId, @Path("itemId") long itemId, @Body Item item, @Header("Authorization") String token);

    @PUT("/api/categories/{categoryId}/items/{itemId}/updatePrice")
    Call<Item> updateItemPrice(@Path("categoryId") long categoryId, @Path("itemId") long itemId, @Body Item item, @Header("Authorization") String token);

    @DELETE("/api/categories/{categoryId}/items/{itemId}")
    Call<?> deleteItem(@Path("categoryId") long categoryId, @Path("itemId") long itemId, @Body Item item, @Header("Authorization") String token);


}







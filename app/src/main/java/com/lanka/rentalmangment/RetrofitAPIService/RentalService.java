package com.lanka.rentalmangment.RetrofitAPIService;


import com.lanka.rentalmangment.CallBacks.CustomizeCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.DTO.Responses.RentalResponse;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.Models.RequestedDate;
import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.RentalApi;

import java.util.List;

import retrofit2.Call;

public class RentalService {
    RentalApi rentalApi;
    RentalApi rentalApiDate;
    Connection connection;

    public RentalService() {
        this.rentalApi = connection.getRetrofitClientInstance().create(RentalApi.class);
        this.rentalApiDate= Connection.getRetrofitDateInstance().create(RentalApi.class);
    }

    public void getAllRentalsByItemId(String token,long itemId, ResponseCallback callback) {
        Call<List<Rental>> call = rentalApi.getAllRentalsByItemId(itemId, token);
        call.enqueue(new CustomizeCallback<List<Rental>>(callback));
    }
    public void getAllRentalsByUserId(String token, long userId,ResponseCallback callback) {
        Call<List<RentalResponse>> call = rentalApi.getAllRentalsByUserId(userId, token);
        call.enqueue(new CustomizeCallback<List<RentalResponse>>(callback));
    }
    public void getMyOrders(long userId, String token,ResponseCallback callback) {
        Call<List<RentalResponse>> call = rentalApi.getMyOrders(userId, token);
        call.enqueue(new CustomizeCallback<List<RentalResponse>>(callback));
    }


    public void getAllRentalsByUserIdAndStatus(long userId,String status, String token, ResponseCallback callback) {
        Call<List<RentalResponse>> call = rentalApi.getAllRentalsByUserIdAndStatus(userId, status, token);
        call.enqueue(new CustomizeCallback<List<RentalResponse>>(callback));
    }

    public void getAllOrdersByUserIdAndStatus(long userId,String status, String token, ResponseCallback callback) {
        Call<List<RentalResponse>> call = rentalApi.getAllOrdersByUserIdAndStatus(userId, status, token);
        call.enqueue(new CustomizeCallback<List<RentalResponse>>(callback));
    }

    public void getAllRentalsByStatus(String status, String token, ResponseCallback callback) {
        Call<List<Rental>> call = rentalApi.getAllRentalsByStatus(status, token);
        call.enqueue(new CustomizeCallback<List<Rental>>(callback));
    }
//
//
    public void getRentalById(long rentalId, String token, ResponseCallback callback){
        Call<RentalResponse>call = rentalApi.getRentalById(rentalId, token);
        call.enqueue(new CustomizeCallback<RentalResponse>(callback));
    }
    public void getAllRentals( String token, ResponseCallback callback){
        Call<List<Rental>> call = rentalApi.getAllRentals(token);
        call.enqueue(new CustomizeCallback<List<Rental>>(callback));
    }

    public void createRental(long itemId,long userId, String token, Rental rental, ResponseCallback callback){
        Call<MessageResponse> call = rentalApi.createRental(itemId, userId, rental,token);
        call.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }
    public void updateRentalReturnDate(long userId, long rentalId, Rental rentalRequest,String token, ResponseCallback callback){
        Call<Rental> call = rentalApi.updateRentalReturnDate(userId, rentalId,rentalRequest, token);
        call.enqueue(new CustomizeCallback<Rental>(callback));
    }
    public void updateRentalStatus(long rentalId,long userId, String token, String status,ResponseCallback callback){
        Call<MessageResponse> call = rentalApi.updateRentalStatus(userId,rentalId, status,token);
        call.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }

    public void checkItemAvailability(long itemId, RequestedDate requestedDate, String token, ResponseCallback callback){
        Call <String> call = rentalApiDate.checkItemAvailability(itemId,requestedDate,token);
        call.enqueue(new CustomizeCallback<String>(callback));
    }

    public void deleteRental(long rentalId, String token, ResponseCallback callback){
        Call call = rentalApi.deleteRental(rentalId,token);
        call.enqueue(new CustomizeCallback<>(callback));
    }
}

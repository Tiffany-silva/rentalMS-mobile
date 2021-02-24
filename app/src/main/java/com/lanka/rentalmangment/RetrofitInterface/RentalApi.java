package com.lanka.rentalmangment.RetrofitInterface;


import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.DTO.Responses.RentalResponse;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.Models.RequestedDate;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RentalApi {


    @GET("/api/items/{itemId}/rentals")
    Call<List<Rental>> getAllRentalsByItemId(@Path("itemId") long itemId,@Header("Authorization") String token);

    @GET("/api/users/{userId}/rentals")
    Call<List<RentalResponse>> getAllRentalsByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    @GET("/api/users/{userId}/myorders")
    Call<List<RentalResponse>> getMyOrders(@Path("userId") long userId, @Header("Authorization") String token);

    @GET("/api/users/{userId}/rentals/{status}")
    Call<List<RentalResponse>> getAllRentalsByUserIdAndStatus(@Path("userId") long userId,@Path("status") String status,@Header("Authorization") String token);

    @GET("/api/rentals/findByStatus/{status}")
    Call<List<Rental>> getAllRentalsByStatus(@Path("status") String status,@Header("Authorization") String token);


    @GET("/api/users/lessor/{userId}/rentals/{status}")
    Call<List<RentalResponse>> getAllOrdersByUserIdAndStatus(@Path("userId") long userId,@Path("status") String status,@Header("Authorization") String token);

    @GET("/api/rentals/{rentalId}")
    Call<RentalResponse> getRentalById(@Path("rentalId") long rentalId,@Header("Authorization") String token);

    @GET("/api/rentals")
    Call<List<Rental>> getAllRentals(@Header("Authorization") String token);

    @POST("/api/items/{itemId}/rentals/{userId}")
    Call<MessageResponse> createRental(@Path("itemId") long itemId, @Path("userId") long userId, @Body Rental rental, @Header("Authorization") String token);


    @PUT("/api/users/{userId}/rentals/{rentalId}/updateReturnDate")
    Call<Rental> updateRentalReturnDate(@Path("userId") long userId,@Path("rentalId") long rentalId,@Body Rental rentalRequest, @Header("Authorization") String token);

    @PUT("/api/users/{userId}/rentals/{rentalId}/updateStatus/{status}")
    Call<MessageResponse> updateRentalStatus(@Path("userId") long userId, @Path("rentalId") long rentalId, @Path("status") String status, @Header("Authorization") String token);


    @POST("/api/items/checkAvailabilityMob/{itemId}")
    Call <String> checkItemAvailability(@Path("itemId") long itemId, @Body RequestedDate requestedDate, @Header("Authorization") String token);



    @DELETE("/rentals/{rentalId}")
    Call<?> deleteRental(@Path("rentalId") long rentalId, @Header("Authorization") String token);

}
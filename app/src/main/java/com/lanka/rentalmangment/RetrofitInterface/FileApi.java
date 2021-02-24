package com.lanka.rentalmangment.RetrofitInterface;

import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.DTO.Responses.ResponseFile;
import com.lanka.rentalmangment.Models.Category;
import com.lanka.rentalmangment.Models.File;
import com.lanka.rentalmangment.Models.ImgBBRes;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FileApi {

    @GET("/files")
    Call<List<ResponseFile>> getListFiles();

    @GET("/files/{id}")
    Call<?> getFile();


//    @POST("/upload")
//    Call<File> uploadFile();

//    @Multipart
    @FormUrlEncoded
    @POST("1/upload")
//    Call<ImgBBRes> uploadImage(@Part MultipartBody.Part part, @Query("key") String key);
    Call<ImgBBRes> uploadImage(@Field("image") String image, @Field("name") String name, @Query("key") String key);

}

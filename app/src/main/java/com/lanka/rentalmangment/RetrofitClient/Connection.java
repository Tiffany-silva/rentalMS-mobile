package com.lanka.rentalmangment.RetrofitClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.lanka.rentalmangment.utils.DateConverter;
import com.lanka.rentalmangment.utils.DateConverter;
import com.lanka.rentalmangment.utils.DateConverterRentalDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {
    String url;

    public String setBaseUrl(){
        return url="http://10.0.2.2:8080";
    }


    public String getBaseUrl(){
        return url;
    }
    private static Retrofit retrofit;
    private static Retrofit retrofitImage;
    private static Retrofit retrofitDate;
    private String imgBBUrl="https://api.imgbb.com/";

    public Connection() {
        setBaseUrl();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create( new GsonBuilder().registerTypeAdapter(LocalDate.class, new DateConverter()).create()))
                .build();
        retrofitImage = new Retrofit.Builder()
                .baseUrl(imgBBUrl)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitDate=new Retrofit.Builder().baseUrl(getBaseUrl()).client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create(
                new GsonBuilder().registerTypeAdapter(Date.class, new DateConverterRentalDate()).create())).build();

    }

    public static Retrofit getRetrofitClientInstance() {
        if (retrofit == null) {
            new Connection();
        }
        return retrofit;
    }
    public static Retrofit getImageClientInstance(){
        if(retrofitImage==null){
            new Connection();

        }
        return retrofitImage;
    }
    public static Retrofit getRetrofitDateInstance(){
        if(retrofitDate==null){
            new Connection();

        }
        return retrofitDate;
    }
    public static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }
}

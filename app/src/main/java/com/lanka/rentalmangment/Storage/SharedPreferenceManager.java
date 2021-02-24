package com.lanka.rentalmangment.Storage;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SharedPreferenceManager {
    public static String SHARED_PREF_INFO = "userSession";

    public static SharedPreferenceManager sharedPreferenceManagerIntance;

    static SharedPreferences user_details;
    // Editor Reference for sharedpref
    SharedPreferences.Editor user_details_editor;
    private Context context;


    public SharedPreferenceManager(Context context){
        user_details = context.getSharedPreferences(SHARED_PREF_INFO,0);

        user_details_editor = user_details.edit();
        user_details_editor.apply();
    }

    public static synchronized SharedPreferenceManager getSharedPreferenceInstance(Context context) {
        if (sharedPreferenceManagerIntance == null) {
            sharedPreferenceManagerIntance = new SharedPreferenceManager(context);
        }
        return sharedPreferenceManagerIntance;
    }

    public void SaveUserLoggedIn(LoginResponse loginResponse) {
        user_details_editor.putString("token", "Bearer "+loginResponse.getToken());
        user_details_editor.putString("id", loginResponse.getId());
        user_details_editor.putString("username", loginResponse.getUsername());
        user_details_editor.putString("email", loginResponse.getEmail());
        user_details_editor.putString("role", loginResponse.getRoles().get(0));
        user_details_editor.putString("imageURL", loginResponse.getImageUrl());
        user_details_editor.putString("contact", loginResponse.getContact());
        user_details_editor.putString("address", loginResponse.getAddress());
        user_details_editor.putString("name", loginResponse.getName());


//        user_details_editor.putString("tokenExpireTime", loginResponse.getTokenExpireTime());
        user_details_editor.putString(SHARED_PREF_INFO, new Gson().toJson(loginResponse));
        user_details_editor.commit();
    }


    public boolean isUserLoggedIn() {
        System.out.println("lalalala2222222");

        if (user_details.contains("token")) {
            System.out.println("lalalala");
            return true;
        }else{
            System.out.println("lalalala111111");
            return false;
        }
    }


    public LoginResponse userget() {
        List <String> roles=new ArrayList<String>();
        String role=user_details.getString("role", null);
        roles.add(role);
        return new LoginResponse(
                user_details.getString("token", null),
                user_details.getString("id", null),
                user_details.getString("username", null),
                user_details.getString("email", null),
                roles,
                user_details.getString("imageURL", null),
                user_details.getString("contact", null)
        ,user_details.getString("address", null),
                user_details.getString("name", null));
    }

    public void clear(){
        user_details_editor.clear().commit();
    }
}

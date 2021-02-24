package com.lanka.rentalmangment.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Requests.LoginRequest;

import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.AuthenticationService;

import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.utils.CustomButton;
import com.lanka.rentalmangment.utils.CustomEditText;
import com.lanka.rentalmangment.utils.CustomTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements ResponseCallback {

    private CustomEditText usernametxt, passwordtxt;
    private CustomButton login;
    private CustomTextView register;
    private AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernametxt =  findViewById(R.id.Username);
        passwordtxt = findViewById(R.id.Password);
        login =  findViewById(R.id.logout_profile);
        register =  findViewById(R.id.btnReg);
        authenticationService = new AuthenticationService();
        LoginResponse loginResponse=SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).userget();

        if(loginResponse.getRoles().get(0)!=null){
            String role=loginResponse.getRoles().get(0);
            selectClass(role);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do the validate first if pass go to login
                validateLogin();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }


// Validation Login email and password
    private void validateLogin(){
        String username = usernametxt.getText().toString();
        String password = passwordtxt.getText().toString();
        if(usernametxt == null || usernametxt.length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
        }
        if(passwordtxt == null || passwordtxt.length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();

        }
        else{
            LoginRequest loginRequest = new LoginRequest(username, password);
            Call <LoginResponse> call = authenticationService.login(loginRequest, this);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        System.out.println(response.body());
                        SharedPreferenceManager.getSharedPreferenceInstance(Login.this).SaveUserLoggedIn(response.body());
                        //login activity for admin
                        System.out.println(SharedPreferenceManager.getSharedPreferenceInstance(Login.this).userget().getToken());

                        String role=response.body().getRoles().get(0);
                        System.out.println(role);
                        selectClass(role);
                    } else {
                        Toast.makeText(Login.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    void selectClass(String role){
        if(role.equals("ROLE_LESSEE")){
            System.out.println("yo lessee");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else if(role.equals("ROLE_LESSOR")){
            System.out.println("lessor viewwwwww");
            System.out.println("yo lessorrrrr");
            Intent intent = new Intent(getApplicationContext(), LessorMainActivity.class);
            startActivity(intent);
        }else if(role.equals("ROLE_ADMIN")){
            System.out.println("yo adminnn");
            Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
            startActivity(intent);
        }
    }

    // register button
    public void register() {
        Intent intent = new Intent(getApplicationContext(), UserSelectActivity.class);
        startActivity(intent);
}

    // forgot password button
    public void ForgotPassword() {
        Toast.makeText(this, "This Service is unavailable", Toast.LENGTH_SHORT).show();
    }


    // on start shardpref
        @Override
    protected void onStart() {
        super.onStart();
        if (SharedPreferenceManager.getSharedPreferenceInstance(this).isUserLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }



    @Override
    public void onSuccess(Response response) {
    LoginResponse loginResponse = (LoginResponse) response.body();
        if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
            SharedPreferenceManager.getSharedPreferenceInstance(Login.this).SaveUserLoggedIn(loginResponse);
            Toast.makeText(this, "Welcomw to Admin Side ", Toast.LENGTH_SHORT).show();
            //login activity for admin
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);

        } else if (loginResponse.getRoles().equals("ROLE_LESSOR")) {
            SharedPreferenceManager.getSharedPreferenceInstance(Login.this).SaveUserLoggedIn(loginResponse);
            Toast.makeText(this, "Welcomw to Lessor Side ", Toast.LENGTH_SHORT).show();
            //login activity for lessor
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }

        else if (loginResponse.getRoles().equals("ROLE_LESSI")) {
            SharedPreferenceManager.getSharedPreferenceInstance(Login.this).SaveUserLoggedIn(loginResponse);
            Toast.makeText(this, "Welcomw to Lessi Side ", Toast.LENGTH_SHORT).show();
            //login activity for lessor for lessi
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(Login.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
        }

}



    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, "Failed to login check your email and password", Toast.LENGTH_SHORT).show();

    }
}
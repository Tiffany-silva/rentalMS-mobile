package com.lanka.rentalmangment.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferenceManager loginResponse=new SharedPreferenceManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        System.out.println(loginResponse);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (loginResponse.isUserLoggedIn()) {
                if (loginResponse.userget().getRoles().get(0).equals("ROLE_ADMIN")) {
                    Intent intent = new Intent(SplashActivity.this, AdminMainActivity.class);
                    startActivity(intent);
                }else if (loginResponse.userget().getRoles().get(0).equals("ROLE_LESSOR")) {
                    Intent intent = new Intent(SplashActivity.this, LessorMainActivity.class);
                    startActivity(intent);
                } else if (loginResponse.userget().getRoles().get(0).equals("ROLE_LESSEE")) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            } else {
                startActivity(new Intent(SplashActivity.this, Login.class));
            }
            finish();
        }, SPLASH_TIME_OUT);
    }
}
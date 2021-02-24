package com.lanka.rentalmangment.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.lanka.rentalmangment.R;

public class UserSelectActivity extends AppCompatActivity {

    LinearLayout lessor, lessee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        lessor = findViewById(R.id.lessor);
        lessee = findViewById(R.id.lessi);


        lessor.setOnClickListener(view -> {
            Intent intent = new Intent(UserSelectActivity.this, signup.class);
            intent.putExtra("ROLE", "Lessor");
            startActivity(intent);
        });

        lessee.setOnClickListener(view -> {
            Intent intent = new Intent(UserSelectActivity.this, signup.class);
            intent.putExtra("ROLE", "Lessee");

            startActivity(intent);
        });

    }
}
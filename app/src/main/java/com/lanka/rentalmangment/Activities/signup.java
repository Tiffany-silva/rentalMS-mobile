package com.lanka.rentalmangment.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.Models.ERole;
import com.lanka.rentalmangment.Models.ImgBBRes;
import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.AuthenticationService;
import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.FileApi;
import com.lanka.rentalmangment.utils.CustomButton;
import com.lanka.rentalmangment.utils.CustomEditText;
import com.lanka.rentalmangment.utils.CustomTextView;


import java.io.ByteArrayOutputStream;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class signup extends AppCompatActivity implements ResponseCallback {

    private CustomButton Register;
    private Button uploadImage;
    private CustomEditText RegisterName, RegisterPassword,RegisterEmail, RegisterUsername, registerContact, registerAddress;
    private CustomTextView Login;
    private AuthenticationService authenticationService;
    private TextView role, imageurl;
    private String photoURL;
    private Bitmap bitmap;

    // constant to compare
    // the activity result code
    private int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String selectedRole = getIntent().getStringExtra("ROLE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authenticationService = new AuthenticationService();
        Register =  findViewById(R.id.Register);
        RegisterName =  findViewById(R.id.RegisterName);
        RegisterEmail =  findViewById(R.id.RegisterEmail);
        RegisterPassword =  findViewById(R.id.RegisterPassword);
        RegisterUsername=findViewById(R.id.RegisterUsername);
        registerAddress=findViewById(R.id.registerAddress);
        registerContact=findViewById(R.id.registerNumberContact);
        Login =  findViewById(R.id.btnSignIn);
        role=findViewById(R.id.role);
        imageurl=findViewById(R.id.imageurl);
        uploadImage=findViewById(R.id.uploadImage);
        role.setText(selectedRole);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        // Register OnCLick
        Register.setOnClickListener(v -> Register());

        // Login OnClick
        Login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), com.lanka.rentalmangment.Activities.Login.class);
            startActivity(intent);
        });

        uploadImage.setOnClickListener(v -> selectImage());

    }
    // the Select Image Button is clicked
    void selectImage() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(i, SELECT_PICTURE);
    }


    @SneakyThrows
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null) {
            //the image URI
            Uri selectedImage = data.getData();
            String path = getRealPathFromURI(selectedImage);
            if (path != null) {
                imageurl.setText(path);
            }
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

        }
    }

    private void Register(){

        String path=imageurl.getText().toString();
        String image = convertToString(bitmap);

        Retrofit retrofit= Connection.getImageClientInstance();
        FileApi fileApi=retrofit.create(FileApi.class);

        Call<ImgBBRes> fileUpload = fileApi.uploadImage(image, path,"659ad79210abf40244561adc10b63988");
        fileUpload.enqueue(new Callback<ImgBBRes>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<ImgBBRes> call, Response<ImgBBRes> response) {
                System.out.println(response);
                if(response.isSuccessful()) {
                    photoURL=response.body().getData().getUrl();
                    System.out.println(response.body().getData().getUrl());
                   sendData();
                }
                Toast.makeText(signup.this, "Success " + response.body().getStatus(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<ImgBBRes> call, Throwable t) {
                Log.d("Errorrrr", "Error " + t.getMessage());
            }
        });
    }
    private String convertToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    private String getRealPathFromURI(Uri contentURI) {

        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return  thePath;
    }

    // if register success this runs
    @Override
    public void onSuccess(Response response) {
        Toast.makeText(this, "Registered Successfully ", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), com.lanka.rentalmangment.Activities.Login.class);
        startActivity(intent);
    }
    // if register fails or any errors this runs
    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, "Failed to Register", Toast.LENGTH_SHORT).show();
    }

    private final Context mContext = this;

    public String getRole(String role){
        if(role=="Lessor"){
            return ERole.ROLE_LESSOR.toString();
        }else if(role=="Lessee"){
            return ERole.ROLE_LESSEE.toString();
        }else if(role=="Admin"){
            return ERole.ROLE_ADMIN.toString();
        }else{
            return ERole.ROLE_LESSEE.toString();
        }
    }

    void sendData(){
        String email = RegisterEmail.getText().toString();
        String name = RegisterName.getText().toString();
        String password = RegisterPassword.getText().toString();
        String username=RegisterUsername.getText().toString();
        String  erole=getRole(role.getText().toString());
        String address=registerAddress.getText().toString();
        String contactNumber=registerContact.getText().toString();

        if(username == null ||name == null ||email == null ||address == null||contactNumber == null || username.trim().length() == 0 || email.trim().length() == 0 ){
            Toast.makeText(this, "Filed's cannot be blank", Toast.LENGTH_SHORT).show();
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
        }
        else {
            if (password.length() > 6) {
                User user = new User(email, username, password, name, photoURL, erole, address, contactNumber);
                authenticationService.register(user, this);
            } else {
                Toast.makeText(getApplicationContext(), "Password should be at least 6 character long", Toast.LENGTH_LONG).show();
            }
        }
    }
}

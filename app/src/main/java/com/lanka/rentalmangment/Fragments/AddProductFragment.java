package com.lanka.rentalmangment.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Models.Category;
import com.lanka.rentalmangment.Models.ImgBBRes;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.RetrofitAPIService.CategoryService;
import com.lanka.rentalmangment.RetrofitAPIService.ItemService;
import com.lanka.rentalmangment.RetrofitClient.Connection;
import com.lanka.rentalmangment.RetrofitInterface.FileApi;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.FragmentAddProductBinding;
import com.lanka.rentalmangment.databinding.FragmentAddProductBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentAddProductBinding fragmentAddProductBinding;
    private ItemService itemService;
    private CategoryService categoryService;
    private TextView itemName, itemPrice, itemDescription, itemQuantity, itemurl;
    private String photoURL;
    private LoginResponse loginResponse;
    private Bitmap bitmap;
    private Spinner spinner;
    private Category selectedCategory;
    private List<String> spinnerCategories=new ArrayList<>();
    private List<Category> categories=new ArrayList<>();
    // the activity result code
    private int SELECT_PICTURE = 200;

    public AddProductFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddProductBinding = fragmentAddProductBinding.inflate(getLayoutInflater());
        View view = fragmentAddProductBinding.getRoot();
        getActivity().setTitle("Add Category");
        super.onViewCreated(view, savedInstanceState);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).userget();
        itemService = new ItemService();
        categoryService= new CategoryService();
        itemName=fragmentAddProductBinding.nameAdd;
        itemPrice=fragmentAddProductBinding.priceAdd;
        itemDescription=fragmentAddProductBinding.descriptionAdd;
        itemQuantity=fragmentAddProductBinding.quantityAdd;
        itemurl=fragmentAddProductBinding.itemurl;
        spinner = fragmentAddProductBinding.spinnerAddProduct;

        spinnerCategories.add("Select Category");
        categoryService.getAllCategory( loginResponse.getToken(), getCategoriesResponseCallback());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,spinnerCategories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        fragmentAddProductBinding.addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        fragmentAddProductBinding.uploadPic.setOnClickListener(v -> selectImage());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentAddProductBinding=fragmentAddProductBinding.bind(view);
    }

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

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
            //the image URI
            Uri selectedImage = data.getData();
            String path = getRealPathFromURI(selectedImage);
            if (path != null) {
                itemurl.setText(path);
            }
            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);

        }
    }


    void addProduct(){
        new AlertDialog.Builder(getContext())
                .setTitle("Add Item")
                .setMessage("Confirm?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        registerProduct();
                    }})
                .setNegativeButton(android.R.string.cancel, null).show();
    }


    public void registerProduct(){

        String path=itemurl.getText().toString();
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
                FancyToast.makeText(getContext(), "Item Registered Successfully", Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            }
            @Override
            public void onFailure(Call<ImgBBRes> call, Throwable t) {
                Log.d("Errorrrr", "Error " + t.getMessage());
            }
        });
    }

    void sendData(){
        String name = itemName.getText().toString();
        String descriptionProd = itemDescription.getText().toString();
        String quantityProd = itemQuantity.getText().toString();
        String priceProd=itemPrice.getText().toString();


        if(name == null || descriptionProd == null || quantityProd == null || priceProd == null){
            Toast.makeText(getContext(), "Filed's cannot be blank", Toast.LENGTH_SHORT).show();
        }
        else {
            Item item = new Item();
            item.setPrice(Double.parseDouble(priceProd));
            item.setDescription(descriptionProd);
            item.setItemName(name);
            item.setQuantity(Integer.parseInt(quantityProd));
            item.setUser(new User());
            item.setCategory(selectedCategory);
            item.setImage(photoURL);
            itemService.createItem(Long.parseLong(loginResponse.getId()),selectedCategory.getId(), item,loginResponse.getToken(), addItemResponseCallback());
        }
    }

    public ResponseCallback getCategoriesResponseCallback() {
        ResponseCallback getCategoriesResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.isSuccessful()){
                    categories=(List<Category>) response.body();
                    for (Category category:categories) {
                        spinnerCategories.add(category.getCategoryName());
                    }
                }
            }
            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages here" + errorMessage);
            }
        };
        return getCategoriesResponseCallback;
    }

    public ResponseCallback addItemResponseCallback() {
        ResponseCallback addItemResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.isSuccessful()){
                    FancyToast.makeText(getContext(), "Item Added Successfully", Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }
            }
            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages here" + errorMessage);
            }
        };
        return addItemResponseCallback;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Select Category")){
        }else {
//            selectedCategory= parent.getItemAtPosition(position).toString();
            selectedCategory=categories.get(position-1);
            System.out.println(selectedCategory.getCategoryName());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

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
        Cursor cursor = getContext().getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return  thePath;
    }


}
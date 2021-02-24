package com.lanka.rentalmangment.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanka.rentalmangment.Adapters.HomeAdapter;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.ItemService;
import com.lanka.rentalmangment.RetrofitAPIService.RentalService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.ActivityCategoryProductBinding;
import com.lanka.rentalmangment.databinding.CommonlistviewBinding;
import com.lanka.rentalmangment.databinding.FragmentHomeBinding;
import com.lanka.rentalmangment.databinding.HomeRecycleViewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CategoryProductActivity extends AppCompatActivity implements ItemClickCallback {

    private RecyclerView recyclerView;
    private ActivityCategoryProductBinding activityCategoryProductBinding;
    private LoginResponse loginResponse;
    private HomeAdapter homeAdapter;
    private Long categoryId;
    Toolbar toolbar;
    private List<Item> itemList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryProductBinding = ActivityCategoryProductBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryProductBinding.getRoot());
        toolbar=activityCategoryProductBinding.toolBarCategory;
        setSupportActionBar(toolbar);

        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).userget();
        recyclerView = activityCategoryProductBinding.includeRecycleViewId.recycleViewId;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        homeAdapter = new HomeAdapter(this, getApplicationContext(), loginResponse.getRoles().get(0));

        ItemService itemService = new ItemService();
        categoryId=(getIntent().getExtras().getLong("categoryId"));
        System.out.println(loginResponse.getRoles().get(0));
        if(loginResponse.getRoles().get(0).equals("ROLE_LESSEE")){
            itemService.getAllItemsByCategory(categoryId,loginResponse.getToken(), getAllItemsOfCategoryResponseCallback());
        }else if(loginResponse.getRoles().get(0).equals("ROLE_LESSOR")){
            itemService.getAllItemByUserAndCategory(categoryId,Long.parseLong(loginResponse.getId()), loginResponse.getToken(), getAllItemsByCategoryAndUserResponseCallback());
        }
    }

    public ResponseCallback getAllItemsOfCategoryResponseCallback() {
        final ResponseCallback getAllItemsOfCategoryResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<Item> itemList = (List<Item>) response.body();
                if(itemList!=null){
                    recyclerView.setAdapter(homeAdapter);
                    homeAdapter.setAllProductData(itemList);
                    System.out.println(itemList.get(0).getItemName());
                    homeAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onError(String errorMessage) {
                if (errorMessage == null) {
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    System.out.println("error messages " + errorMessage);
                }
            }
        };
        return getAllItemsOfCategoryResponseCallback;
    }

    public ResponseCallback getAllItemsByCategoryAndUserResponseCallback() {
        final ResponseCallback getAllItemsByCategoryAndUserResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<Item> itemList = (List<Item>) response.body();
                if(itemList!=null){
                    recyclerView.setAdapter(homeAdapter);
                    homeAdapter.setAllProductData(itemList);
                     homeAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onError(String errorMessage) {
                if (errorMessage == null) {
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    System.out.println("error messages " + errorMessage);
                }
            }
        };
        return getAllItemsByCategoryAndUserResponseCallback;
    }

    @Override
    public void onItemClickListener(Long id) {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

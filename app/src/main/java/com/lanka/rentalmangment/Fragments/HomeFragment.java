package com.lanka.rentalmangment.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanka.rentalmangment.Adapters.HomeAdapter;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.RetrofitAPIService.ItemService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.FragmentHomeBinding;
import com.lanka.rentalmangment.databinding.HomeRecycleViewBinding;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ItemClickCallback {
    private HomeAdapter homeAdapter;
    private FragmentHomeBinding fragmentHomeBinding;
    private LoginResponse loginResponse;
    private ItemService itemService;
    private RecyclerView recyclerView;
    private HomeRecycleViewBinding homeRecycleViewBinding;


    List<Item> productList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @SneakyThrows
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        homeRecycleViewBinding=fragmentHomeBinding.includeRecycleViewId;
        View view = fragmentHomeBinding.getRoot();
        recyclerView =homeRecycleViewBinding.recycleViewId;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getActivity().setTitle("Home");


        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).userget();
        homeAdapter =new HomeAdapter(this, getContext(),loginResponse.getRoles().get(0));
        recyclerView.setAdapter(homeAdapter);
        itemService = new ItemService();
        System.out.println(loginResponse.getToken());

        if(loginResponse.getRoles().get(0).equals("ROLE_LESSOR")){
            System.out.println("im inf fragmnt lessor");
            itemService.getAllItemsByUserId(Long.parseLong(loginResponse.getId()),loginResponse.getToken(),lessorProductResponseCallback());
        }else if(loginResponse.getRoles().get(0).equals("ROLE_LESSEE")){
            itemService.getAllItems(loginResponse.getToken(),lesseeProductResponseCallback());
            System.out.println("im inf fragmnt lessee");
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentHomeBinding=fragmentHomeBinding.bind(view);
        homeAdapter.setAllProductData(productList);
    }
    @Override
    public void onItemClickListener(Long id) {
        if (loginResponse.getRoles().equals("ROLE_LESSEE")) {
//            startActivity(new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId", id));
        }
//        else if (loginResponse.getRoles().equals("ROLE_LESSEE")) {
//            startActivity(new Intent(this, ProductDetailActivity.class).putExtra("productId", id));
//        }else if (loginResponse.getRoles().equals("ROLE_LESSOR")) {
//            startActivity(new Intent(this, ProductDetailActivity.class).putExtra("productId", id));
//        }
    }
    public ResponseCallback lessorProductResponseCallback() {
        ResponseCallback lessorProductsResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                    productList = (List<Item>) response.body();
                    homeAdapter.setAllProductData(productList);
                    homeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(String errorMessage) {
            }
        };
        return lessorProductsResponseCallback;
    }
    public ResponseCallback lesseeProductResponseCallback() {
        ResponseCallback lesseeProductResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                productList = (List<Item>) response.body();
                homeAdapter.setAllProductData(productList);
                homeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(String errorMessage) {
            }
        };
        return lesseeProductResponseCallback;
    }
}
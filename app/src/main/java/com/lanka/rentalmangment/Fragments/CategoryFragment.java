package com.lanka.rentalmangment.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanka.rentalmangment.Adapters.CategoryAdapter;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Models.Category;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.CategoryService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.CommonlistviewBinding;
import com.lanka.rentalmangment.databinding.FragmentCategoryBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class CategoryFragment extends Fragment implements ItemClickCallback {

    private CommonlistviewBinding commonlistviewBinding;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryService categoryService;
    private LoginResponse loginResponse;
    List<Category> categoryList = new ArrayList<>();

    public CategoryFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        commonlistviewBinding = commonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        getActivity().setTitle("Categories");
        super.onViewCreated(view, savedInstanceState);
        recyclerView = commonlistviewBinding.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).userget();
        categoryAdapter = new CategoryAdapter(this, getContext(), loginResponse.getRoles().get(0));
        recyclerView.setAdapter(categoryAdapter);

        categoryService = new CategoryService();
        categoryService.getAllCategory(loginResponse.getToken(),categoryResponseCallBack());


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        fragmentCategoryBinding=fragmentCategoryBinding.bind(view);
        categoryAdapter.setAllProductData(categoryList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public ResponseCallback categoryResponseCallBack() {
        ResponseCallback categoryResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                categoryList = (List<Category>) response.body();
                System.out.println(categoryList.get(0).getCategoryName());
                recyclerView.setVisibility(View.VISIBLE);
//                commonlistviewBinding.emptyView.setVisibility(View.GONE);
                categoryAdapter.setAllProductData(categoryList);
                categoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages hererere" + errorMessage);
            }
        };
        return categoryResponseCallBack;
    }
    @Override
    public void onItemClickListener(Long id) {
//        Log.e("clicked product detail:", String.valueOf(id));
//        startActivity(new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId", id));
    }

}

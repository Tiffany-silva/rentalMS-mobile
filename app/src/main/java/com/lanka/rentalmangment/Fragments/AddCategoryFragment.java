package com.lanka.rentalmangment.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanka.rentalmangment.Adapters.CategoryAdapter;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.Models.Category;
import com.lanka.rentalmangment.Models.EStatus;
import com.lanka.rentalmangment.RetrofitAPIService.CategoryService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.CommonlistviewBinding;
import com.lanka.rentalmangment.databinding.FragmentCategoryBinding;
import com.lanka.rentalmangment.databinding.FragmentCategoryBinding;
import com.lanka.rentalmangment.utils.CustomButton;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends Fragment implements ItemClickCallback {

    private FragmentCategoryBinding fragmentCategoryBinding;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryService categoryService;
    private LoginResponse loginResponse;
    private EditText addCategoryText;
    private CustomButton registerCategoryBtn;

    List<Category> categoryList = new ArrayList<>();

    public AddCategoryFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCategoryBinding = fragmentCategoryBinding.inflate(getLayoutInflater());
        View view = fragmentCategoryBinding.getRoot();
        getActivity().setTitle("Categories");
        super.onViewCreated(view, savedInstanceState);
        addCategoryText=fragmentCategoryBinding.categoryNameAdd;
        registerCategoryBtn=fragmentCategoryBinding.addCategoryBtn;
        recyclerView = fragmentCategoryBinding.commonListCategory.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).userget();
        categoryAdapter = new CategoryAdapter(this, getContext(), loginResponse.getRoles().get(0));
        recyclerView.setAdapter(categoryAdapter);

        categoryService = new CategoryService();
        categoryService.getAllCategory(loginResponse.getToken(), categoryResponseCallBack());
        registerCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategoryAlert();
            }
        });
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

    public void addCategoryAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Add Category")
                .setMessage("Confirm?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Category category=new Category();
                        category.setCategoryName(addCategoryText.getText().toString());
                        categoryService.saveCategory(category, loginResponse.getToken(),saveCategoryResponseCallBack());
                    }})
                .setNegativeButton(android.R.string.cancel, null).show();
    }


    public ResponseCallback categoryResponseCallBack() {
        ResponseCallback categoryResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                categoryList = (List<Category>) response.body();
                recyclerView.setVisibility(View.VISIBLE);
                categoryAdapter.setAllProductData(categoryList);
                categoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages here" + errorMessage);
            }
        };
        return categoryResponseCallBack;
    }

    public ResponseCallback saveCategoryResponseCallBack() {
        ResponseCallback saveCategoryResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Category category = (Category) response.body();
                categoryList.add(category);
                recyclerView.setVisibility(View.VISIBLE);
                categoryAdapter.setAllProductData(categoryList);
                categoryAdapter.notifyDataSetChanged();
                FancyToast.makeText(getContext(), "Category Added Successfully", Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

            }
            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages hererere" + errorMessage);
            }
        };
        return saveCategoryResponseCallBack;
    }
    @Override
    public void onItemClickListener(Long id) {
//        Log.e("clicked product detail:", String.valueOf(id));
//        startActivity(new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId", id));
    }

}

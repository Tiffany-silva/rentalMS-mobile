package com.lanka.rentalmangment.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.lanka.rentalmangment.Activities.SplashActivity;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.UserService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.FragmentProfileBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding fragmentProfileBinding;
    LoginResponse loginResponse;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = fragmentProfileBinding.getRoot();
        getActivity().setTitle("My Profile");
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).userget();
        fragmentProfileBinding.usernameProfile.setText("Username   " +loginResponse.getUsername());
        fragmentProfileBinding.emailProfile.setText("Email           "+loginResponse.getEmail());
        fragmentProfileBinding.roleProfile.setText("Role            "+loginResponse.getRoles().get(0));
        fragmentProfileBinding.contactProfile.setText("Contact      "+loginResponse.getContact());
        fragmentProfileBinding.addressProfile.setText("Address     "+loginResponse.getAddress());
        fragmentProfileBinding.nameProfile.setText(loginResponse.getName());
        Picasso.get()
                .load(loginResponse.getImageUrl())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(fragmentProfileBinding.imageProfile);
        fragmentProfileBinding.logoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceManager.getSharedPreferenceInstance(getContext()).clear();
                startActivity(new Intent(getContext(), SplashActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}


package com.lanka.rentalmangment.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.lanka.rentalmangment.Adapters.TabPageAdapter;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
//import com.lanka.rentalmangment.Models.CartOrders;
import com.lanka.rentalmangment.Models.EStatus;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.R;
//import com.lanka.rentalmangment.RetrofitAPIService.CartOrdersService;
import com.lanka.rentalmangment.RetrofitAPIService.RentalService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.FragmentOrdersBinding;
import com.google.android.material.tabs.TabLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
//setting p tabs in a fragment
public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding fragmentOrdersBinding;
    private RentalService rentalService;
    private LoginResponse loginResponse;
    private ResponseCallback updateOrderResponseCallBack;
    private TabPageAdapter tabPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    List<Rental> rentals = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentOrdersBinding = FragmentOrdersBinding.inflate(getLayoutInflater());
        View view = fragmentOrdersBinding.getRoot();

        viewPager = fragmentOrdersBinding.orderViewpagerId;
        setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = fragmentOrdersBinding.orderTabId;
        tabLayout.addTab(tabLayout.newTab().setText("All Orders"));
        tabLayout.addTab(tabLayout.newTab().setText("Booked"));
        tabLayout.addTab(tabLayout.newTab().setText("Picked"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        rentalService = new RentalService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).userget();
        if(loginResponse.getRoles().get(0).equals("ROLE_LESSOR")){
            getActivity().setTitle("My Orders");
//            rentalService.getMyOrders(loginResponse.getId(), loginResponse.getToken(), getmyOrdersResponseCallback());

        }else {
            getActivity().setTitle("My Rentals");
        }
        return view;
    }

//    public ResponseCallback getMyOrdersResponseCallback() {
//        final ResponseCallback getMyOrdersResponseCallback = new ResponseCallback() {
//            @Override
//            public void onSuccess(Response response) {
//                if(response.isSuccessful()){
//                    rentals = (List<Rental>) response.body();
//                    if(rentals!=null){
//                        recyclerView.setAdapter(homeAdapter);
//                        homeAdapter.setAllProductData(itemList);
//                        System.out.println(itemList.get(0).getItemName());
//                        homeAdapter.notifyDataSetChanged();
//                    }
//                }
//
//
//
////            for (Item item : itemList) {
////                itemList.add(rental.getItem());
////
//
//            }
//            @Override
//            public void onError(String errorMessage) {
//                if (errorMessage == null) {
//                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
//                    System.out.println("error messages " + errorMessage);
//                }
//            }
//        };
//        return getAllItemsOfCategoryResponseCallback;
//    }

    public void setViewPager(ViewPager viewPager) {
        tabPagerAdapter = new TabPageAdapter(getChildFragmentManager());
        Fragment allOrders=new AllOrdersOfUser();
//        Bundle bundle = new Bundle();
//        bundle.putString("status", "ALL ORDERS");
//        allOrders.setArguments(bundle);
        Fragment booked=new BookedRentalFragment();
//        Bundle bundleBooked = new Bundle();
//        bundleBooked.putString("status", "BOOKED");
//        allOrders.setArguments(bundleBooked);
        Fragment completed=new CompletedRentalFragment();
//        Bundle bundleCompleted = new Bundle();
//        bundleCompleted.putString("status", "COMPLETED");
//        allOrders.setArguments(bundleCompleted);
        Fragment cancelled=new CancelledRentalFragment();
        Fragment picked=new PickedRentalFragment();

//        Bundle bundleCancelled = new Bundle();
//        bundleCancelled.putString("status", "CANCELLED");
//        allOrders.setArguments(bundleCancelled);

        tabPagerAdapter.onAddFragment(allOrders, "All Orders");
        tabPagerAdapter.onAddFragment(booked, "Booked");
        tabPagerAdapter.onAddFragment(completed, "Completed");
        tabPagerAdapter.onAddFragment(cancelled, "Cancelled");
        tabPagerAdapter.onAddFragment(picked, "Picked");


//        tabPageAdapter.onAddFragment(new CancelledRentalFragment(), "Cancelled");
        viewPager.setAdapter(tabPagerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    @Override
//    public void onSuccess(Response response) {
//        if(response!=null){
//            List<CartOrders> cartOrdersList = (List<CartOrders>) response.body();
//            for (final CartOrders cartOrders : cartOrdersList) {
//                if (cartOrders.getOrders().getStatus().equals("Pending")) {
//                    new java.util.Timer().schedule(
//                            new java.util.TimerTask() {
//                                @Override
//                                public void run() {
//                                    cartOrders.getOrders().setStatus("Completed");
//                                    cartOrdersService.updateOrderStatus(cartOrders.getCardOrderId(),cartOrders,"Bearer "+loginResponse.getToken(),updateOrderResponseCallBack);
//                                      }
//                            },
////                            60000
//                            120000 //timer for 2 minutes
////                        300000 //timer for 5 minutes
////                        86400000 //timer set for one day
//                    );
//                }
//            }
//        }

//    }

//    @Override
//    public void onError(String errorMessage) {
//
//    }
}


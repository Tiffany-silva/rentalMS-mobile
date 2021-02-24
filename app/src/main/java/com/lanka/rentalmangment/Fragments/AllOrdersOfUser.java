package com.lanka.rentalmangment.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanka.rentalmangment.Adapters.HomeAdapter;
import com.lanka.rentalmangment.Adapters.OrderAdapter;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.DTO.Responses.RentalResponse;
import com.lanka.rentalmangment.Models.EStatus;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.ItemService;
import com.lanka.rentalmangment.RetrofitAPIService.RentalService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.FragmentAllOrdersOfUserBinding;
import com.lanka.rentalmangment.databinding.FragmentHomeBinding;
import com.lanka.rentalmangment.databinding.HomeRecycleViewBinding;
import com.lanka.rentalmangment.databinding.OrdersRecyclerViewBinding;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AllOrdersOfUser extends Fragment implements ItemClickCallback {
    private OrderAdapter orderAdapter;
    private FragmentAllOrdersOfUserBinding fragmentAllOrdersOfUserBinding;
    private LoginResponse loginResponse;
    private RentalService rentalService;
    private RecyclerView recyclerView;
    private OrdersRecyclerViewBinding ordersRecyclerViewBinding;
    private TextView isAvailable;


    List<RentalResponse> rentalList = new ArrayList<>();
    List<RentalResponse> bookedList=new ArrayList<>();
    List<RentalResponse> completedList=new ArrayList<>();
    List<RentalResponse> cancelledList=new ArrayList<>();
    List<RentalResponse> pickedList=new ArrayList<>();

    public AllOrdersOfUser(){}

    @SneakyThrows
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAllOrdersOfUserBinding = FragmentAllOrdersOfUserBinding.inflate(getLayoutInflater());
        ordersRecyclerViewBinding=fragmentAllOrdersOfUserBinding.includeOrdersRecyclerView;
        View view = fragmentAllOrdersOfUserBinding.getRoot();
        recyclerView =ordersRecyclerViewBinding.orderRecycleViewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        isAvailable=fragmentAllOrdersOfUserBinding.isAvailable;

        getActivity().setTitle("My Orders");

        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).userget();
        orderAdapter =new OrderAdapter(this, getContext(),loginResponse.getRoles().get(0));
        recyclerView.setAdapter(orderAdapter);
        rentalService = new RentalService();
        System.out.println(loginResponse.getToken());

        if(loginResponse.getRoles().get(0).equals("ROLE_LESSOR")){
            System.out.println("im in fragmnt lessor");
            rentalService.getMyOrders(Long.parseLong(loginResponse.getId()),loginResponse.getToken(),lessorOrderResponseCallback());

        }else if(loginResponse.getRoles().get(0).equals("ROLE_LESSEE")){
            rentalService.getAllRentalsByUserId(loginResponse.getToken(),Long.parseLong(loginResponse.getId()), lesseeOrderResponseCallback());
            System.out.println("im inf fragmnt lessee");
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentAllOrdersOfUserBinding=fragmentAllOrdersOfUserBinding.bind(view);

        orderAdapter.setRentalList(rentalList);

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
    public ResponseCallback lessorOrderResponseCallback() {
        ResponseCallback lessorOrderResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.isSuccessful()){
                    rentalList = (List<RentalResponse>) response.body();

                    if(rentalList!=null){
                        System.out.println(rentalList.get(0).getItem().getItemName());
                        orderAdapter.setRentalList(rentalList);

                        orderAdapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onError(String errorMessage) {
            }
        };
        return lessorOrderResponseCallback;
    }
    public ResponseCallback lesseeOrderResponseCallback() {
        ResponseCallback lesseeOrderResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.isSuccessful()){
                    rentalList = (List<RentalResponse>) response.body();
                    if(rentalList!=null){
                        orderAdapter.setRentalList(rentalList);
                        if(rentalList.size()==0){
                            isAvailable.setText("No Rentals Available");
                        }
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onError(String errorMessage) {
            }
        };
        return lesseeOrderResponseCallback;
    }

    public void setBookedList(List<RentalResponse> list){
        for(RentalResponse rentalItem: list){
            if(rentalItem.getStatus().equals(EStatus.BOOKED.toString())){
                bookedList.add(rentalItem);
            }
        }
    }

    public void setCompletedList(List<RentalResponse> list){
        for(RentalResponse rentalItem: list){
            if(rentalItem.getStatus().equals(EStatus.COMPLETED.toString())){
                completedList.add(rentalItem);
            }
        }
    }

    public void setCancelledList(List<RentalResponse> list){
        for(RentalResponse rentalItem: list){
            if(rentalItem.getStatus().equals(EStatus.CANCELLED.toString())){
                cancelledList.add(rentalItem);
            }
        }
    }


}
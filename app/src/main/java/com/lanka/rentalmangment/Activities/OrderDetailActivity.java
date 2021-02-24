package com.lanka.rentalmangment.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.DTO.Responses.RentalResponse;
import com.lanka.rentalmangment.Models.EStatus;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.Models.RequestedDate;
import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.ItemService;
import com.lanka.rentalmangment.RetrofitAPIService.RentalService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.ActivityOrderDetailBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class OrderDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ActivityOrderDetailBinding activityOrderDetailBinding;
    private RentalService rentalService;
    private Spinner spinner;
    private String selectedStatus;
    private long rentalId;
    private RentalResponse rental;
    private LoginResponse loginResponse;
    private List<String> statuses=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderDetailBinding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        View view = activityOrderDetailBinding.getRoot();
        setContentView(view);

        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).userget();
        rentalService = new RentalService();
        statuses.add("Select Status");
        if(loginResponse.getRoles().get(0).equals("ROLE_LESSOR")){
            statuses.add(EStatus.COMPLETED.toString());
            statuses.add(EStatus.CANCELLED.toString());
        }else if(loginResponse.getRoles().get(0).equals("ROLE_LESSEE")){
            statuses.add(EStatus.PICKED.toString());
            statuses.add(EStatus.CANCELLED.toString());
        }

        spinner = activityOrderDetailBinding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,statuses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        rentalId = getIntent().getExtras().getLong("rentalId");
        rentalService.getRentalById(rentalId, loginResponse.getToken(), rentalDetailResponseCallBack());
        activityOrderDetailBinding.updateStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    updateAlertbtn();
                }
            });

    }

    public void updateAlertbtn(){
        new AlertDialog.Builder(this)
                .setTitle("Update Status")
                .setMessage("Proceed with updating status of the rental?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        rentalService.updateRentalStatus(rentalId, Long.parseLong(loginResponse.getId()),loginResponse.getToken(), selectedStatus,updateStatusResponseCallBack());
//                        Toast.makeText(ProductDetailActivity.this, "Item rented successfully", Toast.LENGTH_SHORT).show();
                        rental.setStatus(EStatus.valueOf(selectedStatus));
                    }})
                .setNegativeButton(android.R.string.cancel, null).show();
}

    public ResponseCallback rentalDetailResponseCallBack() {
        ResponseCallback rentalDetailResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                rental = (RentalResponse) response.body();
                if(rental.getStatus().equals(EStatus.COMPLETED)|| rental.getStatus().equals(EStatus.CANCELLED)){
                    activityOrderDetailBinding.updateStatusBtn.setVisibility(View.GONE);;
                    activityOrderDetailBinding.spinner.setVisibility(View.GONE);
                }
                Picasso.get()
                        .load(rental.getItem().getImage())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(activityOrderDetailBinding.productImageViewDetail);
                System.out.println(rental.getStatus());
                if(loginResponse.getRoles().get(0).equals("ROLE_LESSOR")){
                    activityOrderDetailBinding.lesseeAddress.setText(rental.getUser().getAddress());
                    activityOrderDetailBinding.lesseeContact.setText(rental.getUser().getContactNumber());
                    activityOrderDetailBinding.lesseeName.setText(rental.getUser().getName());
                    activityOrderDetailBinding.contactInfoText.setText("Lessee Contact Info");

                }else if(loginResponse.getRoles().get(0).equals("ROLE_LESSEE")){
                    activityOrderDetailBinding.lesseeAddress.setText(rental.getItem().getUser().getAddress());
                    activityOrderDetailBinding.lesseeContact.setText(rental.getItem().getUser().getContactNumber());
                    activityOrderDetailBinding.lesseeName.setText(rental.getItem().getUser().getName());
                    activityOrderDetailBinding.contactInfoText.setText("Lessor Contact Info");
                }
                activityOrderDetailBinding.productNameDetail.setText(rental.getItem().getItemName());
                activityOrderDetailBinding.statusText.setText(rental.getStatus().toString());
                activityOrderDetailBinding.rentalDate.setText(rental.getRentalDate());
                activityOrderDetailBinding.returnDate.setText(rental.getReturnDate());
                activityOrderDetailBinding.priceDetail.setText("$ "+rental.getTotalPrice().toString());

            }
            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages here" + errorMessage);
            }
        };
        return rentalDetailResponseCallBack;
    }

    public ResponseCallback updateStatusResponseCallBack() {
        ResponseCallback updateStatusResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                System.out.println(rental.getStatus());
                FancyToast.makeText(getApplicationContext(), "Status updated successfully!", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                activityOrderDetailBinding.statusText.setText(rental.getStatus().toString());
                if(rental.getStatus().equals(EStatus.COMPLETED)|| rental.getStatus().equals(EStatus.CANCELLED)){
                    activityOrderDetailBinding.updateStatusBtn.setVisibility(View.GONE);;
                    activityOrderDetailBinding.spinner.setVisibility(View.GONE);
                }
            }
            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                System.out.println("error messages here" + errorMessage);
            }
        };
        return updateStatusResponseCallBack;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Update Status")){
        }else {
           selectedStatus= parent.getItemAtPosition(position).toString();
           System.out.println(selectedStatus);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

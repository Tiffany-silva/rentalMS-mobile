package com.lanka.rentalmangment.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.lanka.rentalmangment.CallBacks.ResponseCallback;
import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.DTO.Responses.MessageResponse;
import com.lanka.rentalmangment.Models.EStatus;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.Models.RequestedDate;
import com.lanka.rentalmangment.Models.User;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitAPIService.ItemService;
import com.lanka.rentalmangment.RetrofitAPIService.RentalService;
import com.lanka.rentalmangment.Storage.SharedPreferenceManager;
import com.lanka.rentalmangment.databinding.ActivityProductDetailBinding;
import com.lanka.rentalmangment.utils.CustomButton;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity{

    ActivityProductDetailBinding activityProductDetailBinding;
    Item details;
    ItemService itemService;
    RentalService rentalService;
    Long selectedSize;
    LoginResponse loginResponse;
    ImageView productImage;
    TextView price, name, quantity, lessor, address, contact;
    AppCompatButton rentbtn;
    Calendar myCalendar;
    EditText startDate, endDate;
    CustomButton checkAvailability;
    RequestedDate requestedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        myCalendar=Calendar.getInstance();
        productImage=findViewById(R.id.product_image_view_detail);
        name=findViewById(R.id.product_name_detail);
        price= findViewById(R.id.price_detail);
        productImage=findViewById(R.id.product_image_view_detail);
        quantity=findViewById(R.id.quantity_text_detail);
        lessor=findViewById(R.id.owner_name_detail);
        address=findViewById(R.id.address_detail);
        startDate=findViewById(R.id.date_picker_start);
        endDate=findViewById(R.id.date_picker_end);
        contact=findViewById(R.id.contact_detail);
        rentbtn=findViewById(R.id.rent_btn_detail);
        checkAvailability=findViewById(R.id.check_availability);
        setStartDate();
        setEndDate();

        rentbtn.setOnClickListener(v -> rentItem());

        checkAvailability.setOnClickListener(view -> checkAvailability());

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemService = new ItemService();
        rentalService=new RentalService();
        requestedDate=new RequestedDate();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).userget();
        selectedSize=(getIntent().getExtras().getLong("productId"));
        itemService.getItembyId(selectedSize, loginResponse.getToken(), productDetailResponseCallback());

    }

    void checkAvailability(){
        if(requestedDate.getRentalDate()!=null && requestedDate.getReturnDate()!=null){
            System.out.println("im hereeeeeee");
            System.out.println(requestedDate.getReturnDate());
            System.out.println(requestedDate.getRentalDate());

            rentalService.checkItemAvailability(selectedSize,requestedDate,loginResponse.getToken(),checkAvailabilityResponseCallback());
        }
    }

    void rentItem(){
        new AlertDialog.Builder(this)
                .setTitle("Rent Item")
                .setMessage("Proceed with Rental?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    LocalDate localstart=requestedDate.getRentalDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    LocalDate localend=requestedDate.getReturnDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Rental rental=new Rental();
                        rental.setTotalPrice(calculateTotalPrice(details.getPrice(), requestedDate.getRentalDate(), requestedDate.getReturnDate()));

                        System.out.println(localend);
                        rental.setRentalDate(localstart);
                        rental.setReturnDate(localend);
                        rental.setStatus(EStatus.BOOKED);
                        rental.setUser(new User());
                        rental.setItem(new Item());
                        rentalService.createRental(selectedSize, Long.parseLong(loginResponse.getId()), loginResponse.getToken(), rental, rentItemResponseCallback());
//                        Toast.makeText(ProductDetailActivity.this, "Item rented successfully", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.cancel, null).show();
    }

    public ResponseCallback productDetailResponseCallback() {
        final ResponseCallback productResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.isSuccessful()){
                     details= (Item) response.body();
                    if(details!=null){
                        System.out.println(details.getItemName());
                        Picasso.get()
                                .load(details.getImage())
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.error)
                                .into(productImage);
                        price.setText("$ " +details.getPrice().toString());
                        name.setText( details.getItemName());
                        quantity.setText("Available: "+details.getQuantity().toString());
                        lessor.setText("Offered by: "+ details.getUser().getName());
                        address.setText("Location: "+details.getUser().getAddress());
                        contact.setText("Connect on: "+details.getUser().getContactNumber());
                    }
                }
            }
            @Override
            public void onError(String errorMessage) {
                if (errorMessage != null) {
                    FancyToast.makeText(getApplicationContext(), "Sorry something went wrong", Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                }
            }
        };
        return productResponseCallback;
    }
    public ResponseCallback checkAvailabilityResponseCallback() {
        final ResponseCallback checkAvailabilityResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.isSuccessful()){
                    String quantityR= (String)response.body();
                    if(quantityR!=null && Integer.parseInt(quantityR) > 0){
                        quantity.setText("Available: "+quantityR);
                        quantity.setTextColor(Color.parseColor("#097524"));
                        System.out.println(details.getPrice());
                        Double total=calculateTotalPrice(details.getPrice(), requestedDate.getRentalDate(), requestedDate.getReturnDate());
                        price.setText(total.toString());
                    }
                }
            }
            @Override
            public void onError(String errorMessage) {
                if (errorMessage != null) {
                    FancyToast.makeText(getApplicationContext(), "Sorry something went wrong", Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                }
            }
        };
        return checkAvailabilityResponseCallback;
    }

    public ResponseCallback rentItemResponseCallback() {
        final ResponseCallback rentItemResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.isSuccessful()){
                    Toast.makeText(ProductDetailActivity.this,  "Item Rented Successfully", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(String errorMessage) {
                if (errorMessage != null) {
                    FancyToast.makeText(getApplicationContext(), "Sorry something went wrong", Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                }
            }
        };
        return rentItemResponseCallback;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void setStartDate(){
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            System.out.println(requestedDate.getReturnDate());
            requestedDate.setRentalDate(myCalendar.getTime());
            System.out.println(requestedDate.getReturnDate());
            startDate.setText(sdf.format(myCalendar.getTime()));
            System.out.println(myCalendar.getTime());
        };

        startDate.setOnClickListener(v -> {
            new DatePickerDialog(ProductDetailActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    void setEndDate(){
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            requestedDate.setReturnDate(myCalendar.getTime());
            System.out.println(requestedDate.getReturnDate());
            endDate.setText(sdf.format(myCalendar.getTime()));
        };

        endDate.setOnClickListener(v -> {
            new DatePickerDialog(ProductDetailActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    Double calculateTotalPrice(Double price, Date start, Date end){
        LocalDate localstart=start.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        System.out.println(localstart);
        LocalDate localend=end.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int days =(int)ChronoUnit.DAYS.between( localstart , localend );
        System.out.println(days);
        return price * days;
    }
}
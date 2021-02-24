package com.lanka.rentalmangment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lanka.rentalmangment.DTO.Responses.LoginResponse;
import com.lanka.rentalmangment.Models.Rental;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.RetrofitClient.Connection;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViewMyRentalsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<Rental> itemList;

    public ViewMyRentalsAdapter(Context context, List<Rental> itemList) {
        this.context=context;
        this.itemList=itemList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {return itemList.size();}

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_lessor_main,
                    parent, false);
        }
        final Rental currentreq = (Rental) getItem(position);

        final TextView itemName = (TextView) convertView.findViewById(R.id.item_name_order);
        TextView rentalDate = (TextView) convertView.findViewById(R.id.rental_date);
        TextView returnDate = (TextView) convertView.findViewById(R.id.return_date);
        TextView category = (TextView) convertView.findViewById(R.id.category_item_card_id);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity_text_detail);
        TextView price = (TextView) convertView.findViewById(R.id.price_detail);

        rentalDate.setText("rentalDate: "+currentreq.getRentalDate());
        returnDate.setText("returnDate: "+currentreq.getReturnDate());
        itemName.setText("Category: "+currentreq.getItem().getCategory().getId());
        category.setText("Description: "+currentreq.getItem().getDescription());
        quantity.setText("ItemName: "+currentreq.getItem().getItemName());
        price.setText("Price: "+currentreq.getTotalPrice());

        Button ustatus = convertView.findViewById(R.id.update_status_btn);
        ustatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("itemId", String.valueOf(getItemId(position)))
                        .add("status", "BOOKED")
                        .build();

                Connection con = new Connection();
                String url = con.setBaseUrl();
                final Request request = new Request.Builder()
                        .url(url + "/updateStatus")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("Request failed");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("Request Successful!");
                    }
                });
            }

        });
        return convertView;
    }
}

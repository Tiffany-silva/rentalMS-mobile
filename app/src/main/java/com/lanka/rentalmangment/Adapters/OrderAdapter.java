package com.lanka.rentalmangment.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lanka.rentalmangment.Activities.OrderDetailActivity;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.DTO.Responses.RentalResponse;
import com.lanka.rentalmangment.databinding.OrderItemBinding;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private OrderItemBinding orderItemBinding;
    List<RentalResponse> rentalList;
    private ItemClickCallback itemClickCallback;
    String role;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderReturnDate;
        TextView textViewOrderStatus;
        TextView textViewOrderDate;
        TextView textViewOrderTotal;
        CardView cardViewOrderItem;
        TextView itemName;
        public ViewHolder(OrderItemBinding itemBinding) {
            super(itemBinding.getRoot());
            textViewOrderReturnDate = itemBinding.returnDateOrderItem;
            textViewOrderStatus = itemBinding.orderStatusTextviewId;
            textViewOrderDate = itemBinding.rentalDateOrderItem;
            textViewOrderTotal = itemBinding.orderTotalViewId;
            cardViewOrderItem = itemBinding.orderCardviewId;
            itemName=itemBinding.itemNameOrder;
        }
    }

    public OrderAdapter(ItemClickCallback itemClickCallback, Context context, String role) {
        this.itemClickCallback = itemClickCallback;
        this.context=context;
        this.role=role;
    }


    public void setRentalList(List<RentalResponse> rentalList) {
        this.rentalList = rentalList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        orderItemBinding = OrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(orderItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RentalResponse rental = rentalList.get(position);
//        holder.textViewOrderNo.setText(order.getOrdersId().toString());
        holder.textViewOrderStatus.setText(rental.getStatus().toString());
        holder.textViewOrderDate.setText(rental.getRentalDate().toString());
        holder.textViewOrderReturnDate.setText(rental.getReturnDate().toString());
        holder.textViewOrderTotal.setText("USD $" + rental.getTotalPrice().toString());
        holder.itemName.setText(rental.getItem().getItemName());
        holder.cardViewOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("rentalId", rental.getId());
                System.out.println("homeadapter product position: " + rental.getId());
                itemClickCallback.onItemClickListener(rental.getId());
                context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("rentalId", rental.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentalList.size();
    }
}

package com.lanka.rentalmangment.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lanka.rentalmangment.Activities.ProductDetailActivity;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.R;
import com.lanka.rentalmangment.databinding.HomeItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    HomeItemBinding homeItemBinding;
    Context context;
    List<Item> productList;
    private ItemClickCallback itemClickCallback;
    LayoutInflater layoutInflater;
    String role;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewProduct;
        TextView textViewPrice;
        CardView cardView;

        public ViewHolder(HomeItemBinding itemBinding) {
            super(itemBinding.getRoot());
            imageView = itemBinding.itemImageViewId;
            textViewProduct = itemBinding.itemTextViewName;
            textViewPrice = itemBinding.itemTextViewPrice;
            cardView = itemBinding.cardViewId;
        }
    }


    public HomeAdapter(ItemClickCallback itemClickCallback, Context context, String role) {
        this.itemClickCallback = itemClickCallback;
        this.context=context;
        this.role=role;
    }

    public HomeAdapter(Context context, List<Item> productsList) {
        this.context=context;
        this.productList=productsList;
//        layoutInflater = LayoutInflater.from(context);

    }

    public void setAllProductData(List<Item> productsList) {
        this.productList = productsList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        homeItemBinding = HomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(homeItemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item product = productList.get(position);
        Picasso.get()
                .load(product.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);

        holder.textViewProduct.setText(product.getItemName());
        holder.textViewPrice.setText("USD $" + String.valueOf(product.getPrice()));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("homeadapter product position: " + product.getId());
                itemClickCallback.onItemClickListener(product.getId());
                if (role.equals("ROLE_LESSEE")) {
                    context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra("productId", product.getId()));
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickCallback.onItemClickListener(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

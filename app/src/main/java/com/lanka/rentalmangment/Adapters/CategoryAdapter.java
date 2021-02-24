package com.lanka.rentalmangment.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lanka.rentalmangment.Activities.CategoryProductActivity;
import com.lanka.rentalmangment.Activities.ProductDetailActivity;
import com.lanka.rentalmangment.CallBacks.ItemClickCallback;
import com.lanka.rentalmangment.Models.Category;
import com.lanka.rentalmangment.Models.Item;
import com.lanka.rentalmangment.databinding.CategoryItemBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private CategoryItemBinding categoryItemBinding;
    private Context context;
    private List<Category> categoryList;
    private ItemClickCallback itemClickCallback;
    private String role;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategoyrName;
        CardView cardViewCategoryItem;

        public ViewHolder(CategoryItemBinding itemBinding) {
            super(itemBinding.getRoot());
            textViewCategoyrName = itemBinding.categoryItemTextId;
            cardViewCategoryItem = itemBinding.categoryItemCardId;
        }
    }

    public CategoryAdapter(ItemClickCallback itemClickCallback, Context context, String role) {
        this.itemClickCallback = itemClickCallback;
        this.context=context;
        this.role=role;
    }
    public CategoryAdapter(Context context, List<Category> tagsList) {
        this.context = context;
        this.categoryList = tagsList;
        notifyDataSetChanged();
    }

    public void setAllProductData(List<Category> productsList) {
        this.categoryList = productsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        categoryItemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(categoryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        System.out.println("the category is: "+ category);
        holder.textViewCategoyrName.setText(category.getCategoryName());
        holder.cardViewCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("move to the product categories");
                System.out.println("homeadapter product position: " + category.getId());
                itemClickCallback.onItemClickListener(category.getId());
                context.startActivity(new Intent(context, CategoryProductActivity.class).putExtra("categoryId", category.getId()));


//                Intent intent = new Intent(context, CategoryProductActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("categoryName", category.getCategoryName());
//                intent.putExtra("categoryId", category.getId());
//                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

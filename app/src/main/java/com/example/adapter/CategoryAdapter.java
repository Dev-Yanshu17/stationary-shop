package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.model.Category;
import com.example.stationaryshop.CategoryActivity;
import com.example.stationaryshop.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public void setFilter(ArrayList<Category> newList) {
        this.categories = newList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.category_name.setText(category.getCategoryName());
        holder.category_name.setSelected(true);
        Glide.with(context).load(category.getCategoryImage()).into(holder.category_image);

        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, CategoryActivity.class).putExtra("category_name", category.getCategoryName()));
        });
    }
    @Override
    public int getItemCount(){ return categories.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView category_image;
        TextView category_name;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            category_image=itemView.findViewById(R.id.category_image);
            category_name=itemView.findViewById(R.id.category_name);
        }
    }

}

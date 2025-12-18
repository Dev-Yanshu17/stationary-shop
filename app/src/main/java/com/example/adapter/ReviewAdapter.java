package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Review;
import com.example.stationaryshop.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    Context context;
    ArrayList<Review> reviews;

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.review_product_name.setText("Product Name: " + reviews.get(position).getPname());
        holder.review_product_review.setText("Review: " + reviews.get(position).getPreview());
        holder.username.setText("User Name: " + reviews.get(position).getUsername());
    }
    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView review_product_name, review_product_review, username;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            review_product_name = itemView.findViewById(R.id.review_product_name);
            review_product_review = itemView.findViewById(R.id.review_product_review);
            username = itemView.findViewById(R.id.username);
        }
    }
}

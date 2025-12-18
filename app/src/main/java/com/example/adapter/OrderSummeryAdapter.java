package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.model.AddToCart;
import com.example.stationaryshop.R;

import java.util.List;

public class OrderSummeryAdapter extends RecyclerView.Adapter<OrderSummeryAdapter.OrderSummaryViewHolder> {
    private Context context;
    private List<AddToCart> cartItems;

    public OrderSummeryAdapter(Context context, List<AddToCart> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summery, parent, false);
        return new OrderSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryViewHolder holder, int position) {
        AddToCart item = cartItems.get(position);

        // Bind data to views
        Glide.with(context).load(item.getProductImage()).into(holder.productImage);
        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(String.valueOf(item.getProductPrice()));

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    // Method to get the list of cart items
    public List<AddToCart> getItems() {
        return cartItems;
    }

    public static class OrderSummaryViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;

        public OrderSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.OrderProductImage);
            productName = itemView.findViewById(R.id.OrderProductName);
            productPrice = itemView.findViewById(R.id.OrderProductprice);

        }
    }



}

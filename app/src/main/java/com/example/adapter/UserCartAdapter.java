

package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.model.AddToCart;
import com.example.stationaryshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Locale;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AddToCart> addToCarts;
    private TextView totalPriceTextView;
    private  TextView itemCountTextView;
    private DatabaseReference cartReference;

    private int totalItemCount=0;


    public UserCartAdapter(Context context, ArrayList<AddToCart> cartItems,TextView totalPriceTextView,TextView itemCountTextView,DatabaseReference cartRef) {
        this.context = context;
        this. addToCarts= cartItems;
        this.totalPriceTextView=totalPriceTextView;
        this.itemCountTextView=itemCountTextView;
        this.cartReference=cartRef;
        updateItemCount();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddToCart cartItem = addToCarts.get(position);
        holder.bind(cartItem);
        holder.removeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition=holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION){
                    String productId=addToCarts.get(adapterPosition).getProductId();
                    if (productId != null){
                        cartReference.child(productId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Item remove from cart", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to remove item from cart", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    addToCarts.remove(adapterPosition);
                    notifyDataSetChanged();
                    updateTotalPrice();
                    updateItemCount();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return addToCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Views in the cart_item layout
        private ImageView productImageView;
        private TextView productNameTextView;
        private TextView productPriceTextView;
        private TextView quantityTextView;
        private TextView removeText;
        private ImageView plusImageView;
        private ImageView minusImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            productImageView = itemView.findViewById(R.id.CartProductImage);
            productNameTextView = itemView.findViewById(R.id.CartProductName);
            productPriceTextView = itemView.findViewById(R.id.CartProductprice);
            quantityTextView = itemView.findViewById(R.id.quantity);
            removeText = itemView.findViewById(R.id.removeText);
            plusImageView = itemView.findViewById(R.id.plus);
            minusImageView = itemView.findViewById(R.id.minus);
        }
        public void bind(AddToCart item) {
            // Bind data to views
            // Set product image using Glide
            Glide.with(context)
                    .load(item.getProductImage())
                    .into(productImageView);
            productNameTextView.setText(item.getProductName());
            productPriceTextView.setText(String.valueOf(item.getProductPrice()));

            quantityTextView.setText(String.valueOf(item.getQuantity()));

            plusImageView.setOnClickListener(v -> {
                int quantity = item.getQuantity();
                quantity++;
                item.setQuantity(quantity);
                quantityTextView.setText(String.valueOf(quantity));
                // Update total price
                updateTotalPrice();
                // Update item count
                updateItemCount();
            });

            // Minus button click listener
            minusImageView.setOnClickListener(v -> {
                int quantity = item.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    item.setQuantity(quantity);
                    quantityTextView.setText(String.valueOf(quantity));
                    // Update total price
                    updateTotalPrice();
                    // Update item count
                    updateItemCount();
                }
            });
        }
    }
    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (AddToCart item : addToCarts) {
            totalPrice += item.getProductPrice() * item.getQuantity();
        }
        // Set the total price
        totalPriceTextView.setText(String.format(Locale.getDefault(), "Total: %.2f", totalPrice));
    }
    private void updateItemCount() {
        totalItemCount = 0;
        for (AddToCart item : addToCarts) {
            totalItemCount += item.getQuantity();
        }
        // Set the item count
        itemCountTextView.setText(context.getString(R.string.item_count_format, totalItemCount));
    }
    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            // Handle the case when the current user is null (optional)
            return null;
        }
    }
    // Method to calculate total price of all items in the cart
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (AddToCart item : addToCarts) {
            totalPrice += item.getProductPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}

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
import com.example.model.WishList;

import com.example.stationaryshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

public class WishlistAdapter extends FirebaseRecyclerAdapter<WishList, WishlistAdapter.WishlistViewHolder> {
    private Context context;
    private DatabaseReference wishlistRef;
    public WishlistAdapter(@NonNull FirebaseRecyclerOptions<WishList> options, Context context, DatabaseReference wishlistRef) {
        super(options);
        this.context = context;
        this.wishlistRef = wishlistRef;
    }
    @Override
    protected void onBindViewHolder(@NonNull WishlistViewHolder holder, int position, @NonNull WishList model) {
        // Bind data to views
        Glide.with(context).load(model.getPimage()).into(holder.WishlistImage);
        holder.WishlistName.setText(model.getPname());
        holder.WishlistPrice.setText(model.getPprice());

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Get the adapter position
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Get the key of the item to be deleted
                    String wishlistItemId = getSnapshots().getSnapshot(adapterPosition).getKey();
                    // Remove the item from the database
                    if (wishlistItemId != null) {
                        wishlistRef.child(wishlistItemId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // Item removed successfully
                                        Toast.makeText(context, "Item removed from wishlist", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to remove the item
                                        Toast.makeText(context, "Failed to remove item from wishlist", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });


    }
    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new WishlistViewHolder(view);
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder {
        ImageView WishlistImage,deleteImageView;
        TextView WishlistName, WishlistPrice;
        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            WishlistImage = itemView.findViewById(R.id.WishlistImage);
            WishlistName = itemView.findViewById(R.id.WishlistName);
            WishlistPrice = itemView.findViewById(R.id.WishlistPrice);
            deleteImageView = itemView.findViewById(R.id.deleteImageView); // Initialize deleteI

        }
    }
}

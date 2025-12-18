package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.adapter.WishlistAdapter;
import com.example.model.WishList;
import com.example.stationaryshop.databinding.ActivityWishListBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class WishListActivity extends AppCompatActivity {
    WishlistAdapter mainAdapter;
    ActivityWishListBinding binding;

    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_wish_list);

        binding = ActivityWishListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.wishlistrecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<WishList> options =
                new FirebaseRecyclerOptions.Builder<WishList>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("wishlist"), WishList.class)
                        .build();

        mainAdapter = new WishlistAdapter(options, this, FirebaseDatabase.getInstance().getReference().child("wishlist"));
        binding.wishlistrecycler.setAdapter(mainAdapter);


        binding.home.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            overridePendingTransition(0, 0);
        });

        binding.wishlist.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), WishListActivity.class));
            overridePendingTransition(0, 0);
        });




        binding.order.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), OrderActivity.class));
            overridePendingTransition(0, 0);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
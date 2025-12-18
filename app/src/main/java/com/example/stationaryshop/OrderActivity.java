package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.adapter.UserOrderAdapter;
import com.example.adapter.UserOrderHistoryAdapter;
import com.example.database.SqliteDatabase;
import com.example.model.UserOrder;
import com.example.model.UserOrderHistory;
import com.example.stationaryshop.databinding.ActivityOrderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserOrderHistoryAdapter adapter;
    private List<UserOrderHistory> orderList;

    private FirebaseAuth mAuth;
    private DatabaseReference ordersRef;
    ActivityOrderBinding binding;
    boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.userorderrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

        orderList = new ArrayList<>();
        adapter = new UserOrderHistoryAdapter(this, orderList);
        recyclerView.setAdapter(adapter);

        fetchOrderIDsForCurrentUser();

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

        binding.profile.setOnClickListener(v -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
            overridePendingTransition(0, 0);
        });

       
    }

    private void fetchOrderIDsForCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DatabaseReference userOrdersRef = ordersRef.child(userId);

        userOrdersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String orderId = snapshot.getKey();
                    if (orderId != null) {
                        UserOrderHistory orderHistory = new UserOrderHistory(orderId);
                        orderList.add(orderHistory);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderActivity.this, "Failed to fetch order IDs", Toast.LENGTH_SHORT).show();
            }
        });



    }
}

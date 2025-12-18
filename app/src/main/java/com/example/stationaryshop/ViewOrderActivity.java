package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.adminAdapter.AdminOrderHistoryAdapter;
import com.example.model.OrderDetails;
import com.example.stationaryshop.databinding.ActivityViewOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderActivity extends AppCompatActivity {

    private ActivityViewOrderBinding binding;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private AdminOrderHistoryAdapter adapter;
    private List<OrderDetails> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appBar.backBtn.setOnClickListener(v -> onBackPressed());

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        adapter = new AdminOrderHistoryAdapter(this, orderList);
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(ViewOrderActivity.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.activity_loadingview);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        fetchOrders();
    }

    private void fetchOrders() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey(); // Get user ID
                        for (DataSnapshot orderSnapshot : userSnapshot.getChildren()) {
                            OrderDetails order = orderSnapshot.getValue(OrderDetails.class);
                            if (order != null) {
                                order.setUserId(userId); // Set user ID
                                order.setUserName(orderSnapshot.child("userName").getValue(String.class)); // Set user name
                                orderList.add(order);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ViewOrderActivity.this, "No orders found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(ViewOrderActivity.this, "Failed to fetch orders: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

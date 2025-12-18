package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.OrderSummeryAdapter;
import com.example.model.AddToCart;
import com.example.stationaryshop.databinding.ActivityAdminBillPageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class admin_bill_page extends AppCompatActivity {
    private TextView orderIdTextView, nameTextView, totalPriceTextView, paymentMethodTextView, dateTextView;
    private RecyclerView recyclerView;
    private OrderSummeryAdapter adapter;
    private DatabaseReference ordersRef;
    ActivityAdminBillPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bill_page);

        binding = ActivityAdminBillPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBar.backBtn.setOnClickListener(view -> onBackPressed());

        // Initialize TextViews
        orderIdTextView = findViewById(R.id.aorderidtxt);
        nameTextView = findViewById(R.id.anametxt);
        totalPriceTextView = findViewById(R.id.atotalpricetxt);
        paymentMethodTextView = findViewById(R.id.apaymentmethodtxt);
        dateTextView = findViewById(R.id.adatetxt);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.aproductdetailrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve order ID from Intent
        String orderId = getIntent().getStringExtra("orderId");
        // Set the order ID text
        orderIdTextView.setText("Order ID: " + orderId);

        // Initialize Firebase
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ordersRef = rootRef.child("Orders");

        // Retrieve order details from each user's node in the "Orders" table
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        if (userSnapshot.child(orderId).exists()) {
                            DataSnapshot orderSnapshot = userSnapshot.child(orderId);
                            // Retrieve order details
                            String name = orderSnapshot.child("userName").getValue(String.class);
                            // Retrieve total price from the orderSnapshot, not dataSnapshot
                            Long totalPriceLong = orderSnapshot.child("totalPrice").getValue(Long.class);
                            String totalPrice = String.valueOf(totalPriceLong);
                            String paymentMethod = orderSnapshot.child("paymentMethod").getValue(String.class);
                            String orderdatetime = orderSnapshot.child("orderdatetime").getValue(String.class);

                            // Set order details to TextViews
                            nameTextView.setText("Name: " + name);
                            totalPriceTextView.setText("Total Price: " + totalPrice);
                            paymentMethodTextView.setText("Payment Method: " + paymentMethod);
                            dateTextView.setText("Date-Time: " + orderdatetime);

                            // Retrieve and display products associated with the order ID
                            retrieveAndDisplayProducts(orderId, userSnapshot.getKey());
                            return; // Exit the loop after finding the order
                        }
                    }
                    Toast.makeText(admin_bill_page.this, "Order details not found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(admin_bill_page.this, "No orders found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
                Toast.makeText(admin_bill_page.this, "Failed to retrieve order details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void retrieveAndDisplayProducts(String orderId, String userId) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders").child(userId).child(orderId).child("products");
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<AddToCart> productList = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    // Assuming your AddToCart class has fields for size, name, price, and image

                    String name = productSnapshot.child("productName").getValue(String.class);
                    double price = productSnapshot.child("productPrice").getValue(Double.class);
                    String image = productSnapshot.child("productImage").getValue(String.class);

                    // Create a new AddToCart object
                    AddToCart product = new AddToCart();
                    product.setProductName(name);
                    product.setProductPrice(price);
                    product.setProductImage(image);

                    productList.add(product);
                }
                // Set up RecyclerView adapter with the retrieved products
                adapter = new OrderSummeryAdapter(admin_bill_page.this, productList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
                Toast.makeText(admin_bill_page.this, "Failed to retrieve products: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillPageActivity extends AppCompatActivity {
    private TextView orderIdTextView,nameTextView, totalPriceTextView, paymentMethodTextView;
    private TextView dateTextView;
    private RecyclerView recyclerView;
    private OrderSummeryAdapter adapter;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_page);

        // Initialize TextViews
        orderIdTextView = findViewById(R.id.orderidtxt);
        nameTextView = findViewById(R.id.nametxt);
        totalPriceTextView = findViewById(R.id.totalpricetxt);
        paymentMethodTextView = findViewById(R.id.paymentmethodtxt);
        dateTextView = findViewById(R.id.datetxt);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.productdetailrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve order ID from Intent
        String orderId = getIntent().getStringExtra("orderId");
        // Set the order ID text
        orderIdTextView.setText("Order ID: " + orderId);

        // Initialize Firebase
        ordersRef = FirebaseDatabase.getInstance().getReference("Orders").child(orderId);
// Retrieve order details from Firebase Realtime Database using orderId
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders")
                    .child(userId).child(orderId);
            orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve order details
                        String name = dataSnapshot.child("userName").getValue(String.class);
                        Long totalPriceLong = dataSnapshot.child("totalPrice").getValue(Long.class);
                        String totalPrice = String.valueOf(totalPriceLong);
                        String paymentMethod = dataSnapshot.child("paymentMethod").getValue(String.class);
                        String orderdatetime = dataSnapshot.child("orderdatetime").getValue(String.class);



                        // Set order details to TextViews
                        nameTextView.setText("Name: " + name);
                        totalPriceTextView.setText("Total Price: " + totalPrice);
                        paymentMethodTextView.setText("Payment Method: " + paymentMethod);
                        dateTextView.setText("Date-Time: " + orderdatetime);

                        // Retrieve and display products associated with the order ID
                        retrieveAndDisplayProducts(orderId);



                    } else {
                        Toast.makeText(BillPageActivity.this, "Order details not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled event
                    Toast.makeText(BillPageActivity.this, "Failed to retrieve order details", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(BillPageActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveAndDisplayProducts(String orderId) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders")
                    .child(userId).child(orderId).child("products");
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
                    adapter = new OrderSummeryAdapter(BillPageActivity.this, productList);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled event
                    Toast.makeText(BillPageActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(BillPageActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
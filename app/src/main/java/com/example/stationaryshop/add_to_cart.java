package com.example.stationaryshop;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.adapter.UserCartAdapter;
import com.example.model.AddToCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class add_to_cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserCartAdapter adapter;
    private ArrayList<AddToCart> cartItems;
    private TextView totalPriceTextView;
    private  TextView itemCountTextView;
    private DatabaseReference cartReference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        sharedPreferences=this.getSharedPreferences("doctro",MODE_PRIVATE);


        // Initialize views
        recyclerView = findViewById(R.id.userorderrecycler);
        totalPriceTextView=findViewById(R.id.totalPriceTextView);
        itemCountTextView=findViewById(R.id.itemCountTextView);

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            String userId = currentUser.getUid();
            cartReference=FirebaseDatabase.getInstance().getReference().child("cart").child(userId);
            cartReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    cartItems.clear();
                    for (DataSnapshot snapshot : datasnapshot .getChildren()){
                        AddToCart cartItem = snapshot.getValue(AddToCart.class);
                        cartItems.add(cartItem);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        // Set up RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItems = new ArrayList<>();
        adapter = new UserCartAdapter(this, cartItems,totalPriceTextView,itemCountTextView,cartReference);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btncheckout).setOnClickListener(v -> {
            // Calculate total price
            double totalPrice = adapter.calculateTotalPrice();

            // Pass total price to ShippingAddressPageActivity
            Intent intent = new Intent(add_to_cart.this, ShippingAddressPageActivity.class);
            intent.putExtra("TOTAL_PRICE", totalPrice);
            startActivity(intent);
        });

    }

    private void saveCartData(ArrayList<AddToCart> cartItems) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString("cartItems", json);
        editor.apply();
    }

    private void loadCartData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cartItems", null);
        Type type = new TypeToken<ArrayList<AddToCart>>() {}.getType();
        ArrayList<AddToCart> loadedCartItems = gson.fromJson(json, type);

        if (loadedCartItems != null) {
            cartItems.clear(); // Clear existing data
            cartItems.addAll(loadedCartItems); // Add loaded data to cartItems

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }
    }
}





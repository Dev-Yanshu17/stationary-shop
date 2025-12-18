package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CategoryAdapter;
import com.example.adapter.StationaryProductAdapter;
import com.example.model.Category;
import com.example.model.StationaryProduct;
import com.example.stationaryshop.databinding.ActivityHomePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class HomePage extends AppCompatActivity {
    ArrayList<Category> categories = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    ArrayList<StationaryProduct> stationaryProducts = new ArrayList<>();
    StationaryProductAdapter stationaryProductAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    boolean isLogin = false;
    Dialog dialog;
    ActivityHomePageBinding binding;

    private int[] imageIds = { R.drawable.slider1,R.drawable.slider2, R.drawable.slider3, R.drawable.slider4, R.drawable.slider5, R.drawable.slider8}; // Your image resources
    private int currentPosition = 0;
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private android.os.Handler handler;
    private Runnable runnable;
    private final int delay = 2000; // 2 seconds delay for auto image slider
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ratings");


        imageView = findViewById(R.id.imageView);
        relativeLayout = findViewById(R.id.relativeLayout);
        handler = new android.os.Handler();

        startSlider();

        sharedPreferences = this.getSharedPreferences("doctro", MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("is_login", false);
        progressDialog = new ProgressDialog(HomePage.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.searchBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });
        binding.allProducts.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AllProductPage.class)));
        stationaryProductAdapter = new StationaryProductAdapter(this, stationaryProducts);
        binding.recycalerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycalerView.setAdapter(stationaryProductAdapter);
        binding.recycalerView.setHasFixedSize(true);

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
            if (isLogin) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                overridePendingTransition(0, 0);
            }
        });

        categoryAdapter = new CategoryAdapter(this, categories);
        binding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);
        binding.categoryRecyclerView.setHasFixedSize(true);

        // Check if an order has been placed
        SharedPreferences sharedPreferences = getSharedPreferences("order_status", MODE_PRIVATE);
        boolean orderPlaced = sharedPreferences.getBoolean("order_placed", false);
        if (orderPlaced) {
            // Show the rating dialog
            showRatingDialog();

            // Reset the flag to false to ensure the dialog is shown only once
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("order_placed", false);
            editor.apply();
        }
    }
    /*public void onClick(View v) {
        dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.activity_navigation_page);

        // Set the background of the dialog window to be transparent
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Set the layout parameters for the dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(true);
        dialog.show();
        // Set up click listeners for navigation items inside the dialog
        TextView tvhome = dialog.findViewById(R.id.tvhome);
        tvhome.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            dialog.dismiss();
        });

        //TextView tvhome = dialog.findViewById(R.id.tvhome);
        //TextView tvcart = dialog.findViewById(R.id.tvcart);
        //TextView tvwishlist = dialog.findViewById(R.id.tvwishlist);
        //TextView tvpersonalinfo = dialog.findViewById(R.id.tvpersonalinfo);
        //TextView tvmyorder = dialog.findViewById(R.id.tvmyorder);
        //TextView tvmenu = dialog.findViewById(R.id.tvmenu);
        //TextView tvchangepassword = dialog.findViewById(R.id.tvchangepassword);
        //TextView tvratings = dialog.findViewById(R.id.tvratings);
        //TextView tvaboutus = dialog.findViewById(R.id.tvaboutus);
    }*/

    private void startSlider() {
        runnable = new Runnable() {
            @Override
            public void run() {
                currentPosition = (currentPosition + 1) % imageIds.length;
                imageView.setImageResource(imageIds[currentPosition]);
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query1 = FirebaseDatabase.getInstance().getReference("allproduct");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                progressDialog.dismiss();
                stationaryProducts.clear();
                if (dataSnapshot1.exists()) {
                    for (DataSnapshot StationaryProductsnapshot1 : dataSnapshot1.getChildren()) {
                        StationaryProduct StationaryProduct = StationaryProductsnapshot1.getValue(StationaryProduct.class);
                        stationaryProducts.add(StationaryProduct);
                    }
                    stationaryProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query query2 = FirebaseDatabase.getInstance().getReference().child("category");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot1) {
                progressDialog.dismiss();
                categories.clear();
                if (datasnapshot1.exists()) {
                    for (DataSnapshot categorysnapshot1 : datasnapshot1.getChildren()) {
                        Category category = categorysnapshot1.getValue(Category.class);
                        categories.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    public void addtocart(View v){
        Intent i=new Intent(getApplicationContext(), add_to_cart.class);
        startActivity(i);
    }

    public void filter(String text) {
        ArrayList<StationaryProduct> newList = new ArrayList<>();
        for (StationaryProduct item : stationaryProducts) {
            // Search by product name
            if (item.getPname().toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                newList.add(item);
            }
            // Search by description
            else if (item.getPdescription().toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                newList.add(item);
            }

            // Search by price
            else if (String.valueOf(item.getPprice()).toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                newList.add(item);
            }
        }
        stationaryProductAdapter.setFilter(newList);

        // Filter categories
        ArrayList<Category> newListCategory = new ArrayList<>();
        for (Category item : categories) {
            if (item.getCategoryName().toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                newListCategory.add(item);
            }
        }
        categoryAdapter.setFilter(newListCategory);
    }
    private void showRatingDialog() {
        if (!isFinishing()) {
            // Create a dialog and set its content view to your rating app XML layout
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.activity_rate_app);

            // Set the background of the dialog window to be transparent
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Initialize UI elements in the dialog
            RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);
            Button submit = dialog.findViewById(R.id.submit_btn);
            Button cancel = dialog.findViewById(R.id.cancel_btn);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current user
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        final String userId = user.getUid();
                        final float rating = ratingBar.getRating();
                        final String ratingString = String.valueOf(rating);

                        // Fetch user data from the "User:" table
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(userId).exists()) {
                                    // Fetch username from the user data using user ID
                                    String username = dataSnapshot.child(userId).child("username").getValue(String.class);

                                    // Push rating data with username to Firebase Database under user's ID
                                    DatabaseReference userRatingRef = databaseReference.child(userId);
                                    userRatingRef.child("rating").setValue(ratingString);
                                    userRatingRef.child("username").setValue(username);

                                    Toast.makeText(getApplicationContext(), "Rating submitted successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(HomePage.this, HomePage.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "User data not found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Error fetching user data", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        // User is not authenticated
                        Toast.makeText(getApplicationContext(), "User is not authenticated", Toast.LENGTH_SHORT).show();
                    }
                    // Your rating submission logic here
                    dialog.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            // Show the dialog
            dialog.show();
        }
    }

}
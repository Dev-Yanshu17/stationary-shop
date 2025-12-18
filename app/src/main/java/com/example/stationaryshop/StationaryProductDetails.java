package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adapter.ReviewAdapter;
import com.example.model.AddToCart;
import com.example.model.Review;
import com.example.model.StationaryProduct;
import com.example.model.WishList;
import com.example.stationaryshop.databinding.ActivityStationaryProductDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StationaryProductDetails extends AppCompatActivity {
    private  int quality =1;
    private double pricePerUnit = 0;
    boolean isLogin = false;
    String username = "";
    private SharedPreferences sharedPreferences;
    ArrayList<Review> reviews = new ArrayList<>();
    ReviewAdapter reviewAdapter;

    ActivityStationaryProductDetailsBinding binding;
    StationaryProduct stationaryProduct;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_stationary_product_details);
        binding = ActivityStationaryProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = this.getSharedPreferences("doctro", MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("is_login", false);
        username = sharedPreferences.getString("username", "");
        binding.appBar.backBtn.setOnClickListener(view -> onBackPressed());
        binding.appBar.title.setText("Stationary Product");
        stationaryProduct = (StationaryProduct) getIntent().getSerializableExtra("model");


        // Get reference to the "cart" node in Firebase Realtime Database


        Glide.with(this).load(stationaryProduct.pimage).into(binding.stationaryProductImage);
        binding.stationaryProductName.setText("Product Name : " + stationaryProduct.pname);
        binding.stationaryProductDesc.setText("Description :" + stationaryProduct.pdescription);
        binding.stationaryProductPrice.setText("MRP : ₹ " + stationaryProduct.pprice);
//        binding.totalPrice.setText("MRP: ₹ " + beautiProduct.pdprice);
        pricePerUnit = Double.parseDouble(stationaryProduct.pprice);

        binding.reviewRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewAdapter = new ReviewAdapter(this, reviews);
        binding.reviewRecycler.setAdapter(reviewAdapter);
        binding.reviewRecycler.setHasFixedSize(true);
        binding.send.setOnClickListener(view -> {
            if (isLogin) {
                if (binding.reviewEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please Enter Review", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> map = new HashMap<>();
                    sharedPreferences = this.getSharedPreferences("doctro", MODE_PRIVATE);
                    map.put("pdname", stationaryProduct.pname);
                    map.put("username", username);
                    map.put("pdreview", binding.reviewEdit.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("allreviews").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(StationaryProductDetails.this, "Review Sent Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StationaryProductDetails.this, "Error While Sending Review", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Intent i = new Intent(StationaryProductDetails.this, Loginactivity.class);
                startActivity(i);
            }
        });

        binding.addtocart.setOnClickListener(v -> {
            if (isLogin) {
                addToCart();
            } else {
                Intent i = new Intent(StationaryProductDetails.this, Loginactivity.class);
                startActivity(i);
            }
        });

        binding.wishlistIcon.setOnClickListener(v -> addToWishlist());
        // Set image resource to wishlistIcon
        //ImageView wishlistIcon = findViewById(R.id.wishlistIcon);
        //wishlistIcon.setImageResource(R.drawable.wishlistfill)

    }
    private void addToCart(){
        String userId=getCurrentUserId();
        if (!userId.isEmpty()){
            DatabaseReference cartRef=FirebaseDatabase.getInstance().getReference("cart");
            Query query=cartRef.child(userId).orderByChild("ProductName").equalTo(stationaryProduct.getPname());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    if(datasnapshot.exists()){
                        Toast.makeText(StationaryProductDetails.this, "Product already added to cart", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String cartItemId=cartRef.child(userId).push().getKey();
                        AddToCart cartItem =new AddToCart(cartItemId,stationaryProduct.getPname(),stationaryProduct.getPimage(),Double.parseDouble(stationaryProduct.getPprice()));

                        cartRef.child(userId).child(cartItemId).setValue(cartItem)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(StationaryProductDetails.this, "Product  added to cart", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(StationaryProductDetails.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            Toast.makeText(StationaryProductDetails.this, "Failed to fatch user Id", Toast.LENGTH_SHORT).show();
        }
    }
    private String getCurrentUserId(){
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();

        if (currentUser != null){
            return currentUser.getUid();
        }else {
            return "";
        }
    }



 /*   private void addToCart() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");
        String cartItemId = cartRef.push().getKey();
        CartItem cartItem = new CartItem(cartItemId, stationaryProduct.getPname(), stationaryProduct.getPimage(), Double.parseDouble(stationaryProduct.getPprice()));

        cartRef.child(cartItemId).setValue(cartItem)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(StationaryProductDetails.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StationaryProductDetails.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/



    private void addToWishlist() {
        if (isLogin) {
            String userId = sharedPreferences.getString("userId", "");
            String productName = stationaryProduct.getPname();

            // Check if the product is already in the user's wishlist
            Query query = FirebaseDatabase.getInstance().getReference("wishlist")
                    .child(userId)
                    .orderByChild("pname")
                    .equalTo(productName);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // If the product is already in the user's wishlist, remove it
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(StationaryProductDetails.this, "Product removed from wishlist", Toast.LENGTH_SHORT).show();
                                            // Update the UI to reflect the change in the wishlist icon's appearance
                                            binding.wishlistIcon.setImageResource(R.drawable.wishlistunfill); // Change the icon to an empty heart
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(StationaryProductDetails.this, "Failed to remove product from wishlist", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        // If the product is not in the user's wishlist, add it
                        String wishlistId = FirebaseDatabase.getInstance().getReference("wishlist").push().getKey();
                        WishList wishList = new WishList(wishlistId, stationaryProduct.getPimage(), productName, stationaryProduct.getPprice());
                        FirebaseDatabase.getInstance().getReference("wishlist")
                                .child(userId)
                                .child(wishlistId)
                                .setValue(wishList)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(StationaryProductDetails.this, "Product added to wishlist", Toast.LENGTH_SHORT).show();
                                        // Update the UI to reflect the change in the wishlist icon's appearance
                                        binding.wishlistIcon.setImageResource(R.drawable.wishlistfill); // Change the icon to a filled heart
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(StationaryProductDetails.this, "Failed to add product to wishlist", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(StationaryProductDetails.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Intent intent = new Intent(StationaryProductDetails.this, Loginactivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }


    @Override
    public void onStart() {
        super.onStart();
        Query query1 = FirebaseDatabase.getInstance().getReference("allreviews");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                reviews.clear();
                if (dataSnapshot1.exists()) {
                    for (DataSnapshot Reviewsnapshot1 : dataSnapshot1.getChildren()) {
                        Review review = Reviewsnapshot1.getValue(Review.class);
                        reviews.add(review);
                    }
                    reviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
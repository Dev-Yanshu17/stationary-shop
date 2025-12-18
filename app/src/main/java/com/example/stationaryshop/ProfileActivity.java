package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.model.UserProfile;
import com.example.stationaryshop.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
   private ActivityProfileBinding binding;
   private FirebaseAuth mAuth;
   private DatabaseReference userRef;
   private SharedPreferences sharedPreferences;
   private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        sharedPreferences=getSharedPreferences("doctro",MODE_PRIVATE);
        sp=getSharedPreferences("mobile",MODE_PRIVATE);

        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser !=null){
            String userId=currentUser.getUid();
            userRef= FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    UserProfile userProfile=datasnapshot.getValue(UserProfile.class);
                    if (userProfile != null){
                        binding.username.setText(userProfile.getUsername());
                        binding.email.setText(userProfile.getEmail());
                        binding.mno.setText(userProfile.getMno());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        binding.update.setOnClickListener(view -> updateProfileData() );


        binding.home.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            overridePendingTransition(0,0);
        });
        binding.order.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), OrderActivity.class));
            overridePendingTransition(0,0);
        });
        binding.profile.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            overridePendingTransition(0,0);
        });
        binding.wishlist.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), WishListActivity.class));
            overridePendingTransition(0,0);
        });




    }
    private  void updateProfileData(){
        String fullName=binding.username.getText().toString();
        String userEmail=binding.email.getText().toString();
        String mobileNo=binding.mno.getText().toString();

        if (fullName.isEmpty()){
            binding.username.setError("Full name is Required.");
            return;
        }

        if (userEmail.isEmpty()){
            binding.email.setError("Email is Required.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            binding.email.setError("Enter a valid email address.");
            return;
        }
        if (mobileNo.isEmpty()){
            binding.mno.setError("Mobile Number is Required.");
            return;
        }
        if (mobileNo.length() != 10){
            binding.mno.setError("Mobile Number should be 01 digit.");
            return;
        }
        // Create a map to store the updated fields
        Map<String, Object> updates = new HashMap<>();
        updates.put("username", fullName);
        updates.put("email", userEmail);
        updates.put("mno", mobileNo);
        //UserProfile userProfile=new UserProfile(fullName,userEmail,mobileNo);
        // Update the fields in the database
        userRef.updateChildren(updates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    
    public void more(View v){
        Intent i=new Intent(getApplicationContext(),more_details_profile.class);
        startActivity(i);
    }

        /*userRef.setValue(userProfile)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ProfileActivity.this, "Profile update Successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ProfileActivity.this, "Fail to update Profile ", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
    @Override
    public void  onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
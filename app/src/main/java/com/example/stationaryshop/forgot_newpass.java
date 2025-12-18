package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stationaryshop.databinding.ActivityForgotNewpassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class forgot_newpass extends AppCompatActivity {

    private EditText newPasswordEditText, confirmPasswordEditText, etUsernameOrMobile;
    private Button continueButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ActivityForgotNewpassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_forgot_newpass);
        binding = ActivityForgotNewpassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        newPasswordEditText = findViewById(R.id.etnpassr);
        confirmPasswordEditText = findViewById(R.id.etcpassr);
        continueButton = findViewById(R.id.btncontinuefr);
        etUsernameOrMobile = findViewById(R.id.etumfr);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }

    private void changePassword() {
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String usernameOrMobile = etUsernameOrMobile.getText().toString().trim(); // assuming etUsernameOrMobile is the EditText for username or mobile number
        // Check if username or mobile number is provided
        if (TextUtils.isEmpty(usernameOrMobile)) {
            etUsernameOrMobile.setError("Enter username or mobile number!");
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError("Enter new password!");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Confirm your new password!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match!");
            return;
        }

        // Query the database to find the user with provided username or mobile number
        Query query = mDatabase.orderByChild("username").equalTo(usernameOrMobile);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User found by username
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        changeUserPassword(userId, newPassword,confirmPassword);
                    }
                } else {
                    // No user found by username, try with mobile number
                    Query queryMobile = mDatabase.orderByChild("mno").equalTo(usernameOrMobile);
                    queryMobile.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // User found by mobile number
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    String userId = userSnapshot.getKey();
                                    changeUserPassword(userId, newPassword,confirmPassword);
                                }
                            } else {
                                // User not found
                                etUsernameOrMobile.setError("Invalid username or mobile number!");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle database error
                            Toast.makeText(forgot_newpass.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(forgot_newpass.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeUserPassword(String userId, String newPassword,String conformPassword) {
        DatabaseReference userRef = mDatabase.child(userId);

        // Update both password and confirmed password fields
        userRef.child("password").setValue(newPassword);
        userRef.child("conformpasswod").setValue(conformPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password and confirmed password updated successfully in the database
                            Toast.makeText(forgot_newpass.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(forgot_newpass.this, Loginactivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Failed to update password in the database
                            Toast.makeText(forgot_newpass.this, "Failed to update password in the database! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }





}
package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class change_password extends AppCompatActivity {
    private EditText newPasswordEditText, confirmPasswordEditText;
    private Button continueButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        newPasswordEditText = findViewById(R.id.newpass);
        confirmPasswordEditText = findViewById(R.id.conformpass);
        continueButton = findViewById(R.id.conform);

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
        // Password validation regex
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError("Enter new password!");
            return;
        }

        if (!newPassword.matches(passwordRegex)) {
            newPasswordEditText.setError("Password must contain at least one alphabet, one digit, one special character (@#$%^&+=), and be at least 8 characters long");
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


        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Update the password field in your database
                                String uid = mAuth.getCurrentUser().getUid();
                                mDatabase.child(uid).child("password").setValue(newPassword);
                                mDatabase.child(uid).child("conformpasswod").setValue(confirmPassword);

                                Toast.makeText(change_password.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(change_password.this, "Failed to change password! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(change_password.this, "User not authenticated!", Toast.LENGTH_SHORT).show();
        }
    }
}
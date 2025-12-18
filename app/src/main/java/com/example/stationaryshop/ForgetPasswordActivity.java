package com.example.stationaryshop;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stationaryshop.databinding.ActivityForgetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button generateOTPButton;
    private FirebaseAuth mAuth;
    private ActivityForgetPasswordBinding binding; // Initialize ActivityForgotPasswordBinding object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_forget_password);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater()); // Inflate layout using data binding
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.etemail);
        generateOTPButton = findViewById(R.id.btncontinuef);

        generateOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString().trim();
                if (!isValidEmail(email)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkIfEmailExistsInDatabase(email);
            }
        });
    }

    private void checkIfEmailExistsInDatabase(final String email) {
        // Get reference to the "users" table in Firebase database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Email exists in the user table
                    // Generate OTP
                    String otp = generateOTP();
                    // Copy OTP to clipboard
                    copyToClipboard(otp);
                    // Display success message
                    Toast.makeText(ForgetPasswordActivity.this, "OTP copied to clipboard", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPasswordActivity.this, Forget_password_otp_page.class));

                } else {
                    // Email does not exist in the user table
                    Toast.makeText(ForgetPasswordActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Toast.makeText(ForgetPasswordActivity.this, "Failed to check email existence", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private String generateOTP() {
        // Generate a random 4-digit OTP
        Random random = new Random();
        int otp = random.nextInt(9000) + 1000;
        return String.valueOf(otp);
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("OTP", text);
        clipboard.setPrimaryClip(clip);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

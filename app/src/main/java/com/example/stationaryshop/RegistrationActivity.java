package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.User;
import com.example.stationaryshop.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    EditText Email,Username,Mno,Password,Conformpasswod;
    Button Signup;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Email = findViewById(R.id.email);
        Username = findViewById(R.id.name);
        Mno = findViewById(R.id.mno);
        Password = findViewById(R.id.pass);
        Conformpasswod = findViewById(R.id.cpass);
        Signup = findViewById(R.id.b1);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users");
        sharedPreferences = getSharedPreferences("doctro", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    public void login(View v){
        Intent i=new Intent(getApplicationContext(), Loginactivity.class);
        startActivity(i);
    }
    public void call_backsignup(View v){
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(i);
    }
    // Method to validate mobile number format
    /*private boolean isValidMobileNumber(String mobileNumber) {
        // Regular expression to match mobile number format
        String regex = "^[7-9][0-9]{9}$";
        return mobileNumber.matches(regex);
    }*/


    private void registerUser() {
        final String username = Username.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String mno = Mno.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String conformPassword = Conformpasswod.getText().toString().trim();

        // Perform validation checks
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Enter a valid email address");
            Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            Username.setError("Name is required");
            Username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mno)) {
            Mno.setError("Mobile number is required");
            Mno.requestFocus();
            return;
        }
        String mnoreg ="^[7-9][0-9]{9}$";
        if (!mno.matches(mnoreg)) {
            Mno.setError("Enter a valid mobile number starting with 7, 8, or 9 and followed by 9 digits");
            Mno.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            Password.setError("Password should be at least 6 characters");
            Password.requestFocus();
            return;
        }
        String passwordRegex = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=]).{6,}$";
        if (!password.matches(passwordRegex)) {
            Password.setError("Password should contain at least one digit, one alphabet, one special character (@#$%^&+=), and be at least 6 characters long");
            Password.requestFocus();
            return;
        }


        if (!password.equals(conformPassword)) {
            Conformpasswod.setError("Passwords do not match");
            Conformpasswod.requestFocus();
            return;
        }

        // Register the user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Save user details to Firebase Realtime Database
                            String userId = mAuth.getCurrentUser().getUid();
                            User user=new User(userId,email,username,mno,password,conformPassword);
                            ref.child(userId).setValue(user);

                            // Update shared preferences for login status
                            editor.putBoolean("is_login", true);
                            editor.putString("username", username);
                            editor.putString("email", email);
                            editor.apply();

                            // Redirect to the homepage
                            Intent intent = new Intent(RegistrationActivity.this, HomePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // Registration failed
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegistrationActivity.this, "User Account Already Registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}

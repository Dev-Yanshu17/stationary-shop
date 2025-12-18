package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rate_app extends AppCompatActivity {
  /*  RatingBar ratingBar;
    Button submit;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_app);

     /*   // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ratings");

        ratingBar=findViewById(R.id.rating_bar);
        submit=findViewById(R.id.submit_btn);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current user
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    final String userId = user.getUid();
                    final float rating = ratingBar.getRating();
                    final String ratingString = String.valueOf(rating);

                    // Fetch user data from the "users" table
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Fetch username from the user data
                                String username = dataSnapshot.child("username").getValue(String.class);

                                // Push rating data with username to Firebase Database under user's ID
                                DatabaseReference userRatingRef = databaseReference.child(userId);
                                userRatingRef.child("rating").setValue(ratingString);
                                userRatingRef.child("username").setValue(username);

                                Toast.makeText(getApplicationContext(), "Rating submitted successfully", Toast.LENGTH_SHORT).show();
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
            }
        });*/

    }
}

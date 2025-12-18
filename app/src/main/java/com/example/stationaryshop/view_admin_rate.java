package com.example.stationaryshop;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminAdapter.RatingAdapter;
import com.example.model.RatingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class view_admin_rate extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RatingAdapter ratingAdapter;
    private List<RatingModel> ratingList;
    private DatabaseReference ratingsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_rate);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ratingsRef = FirebaseDatabase.getInstance().getReference().child("ratings");
        ratingList = new ArrayList<>();

        ratingAdapter = new RatingAdapter(ratingList);
        recyclerView.setAdapter(ratingAdapter);

        fetchRatings();
    }

    private void fetchRatings() {
        ratingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ratingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String username = snapshot.child("username").getValue(String.class);
                    String rating = snapshot.child("rating").getValue(String.class);
                    ratingList.add(new RatingModel(username, rating));
                }
                ratingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(view_admin_rate.this, "Failed to fetch ratings: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

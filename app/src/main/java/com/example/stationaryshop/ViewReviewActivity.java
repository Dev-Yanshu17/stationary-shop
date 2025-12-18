package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.adminAdapter.AdminViewReviewsAdapter;
import com.example.model.Review;
import com.example.stationaryshop.databinding.ActivityViewReviewBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class    ViewReviewActivity extends AppCompatActivity {
    AdminViewReviewsAdapter mainAdapter;
    ActivityViewReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_review);
        binding = ActivityViewReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBar.title.setText("All Reviews");
        binding.appBar.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Review> options =
                new FirebaseRecyclerOptions.Builder<Review>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("allreviews"), Review.class)
                        .build();

        mainAdapter = new AdminViewReviewsAdapter(options);
        binding.recyclerView.setAdapter(mainAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.adminAdapter.AdminViewUserAdapter;
import com.example.model.LiveUser;
import com.example.stationaryshop.databinding.ActivityViewAllUserBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class ViewAllUserActivity extends AppCompatActivity {
    AdminViewUserAdapter mainAdapter;
    ActivityViewAllUserBinding bining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bining=ActivityViewAllUserBinding.inflate(getLayoutInflater());

       // setContentView(R.layout.activity_view_all_user);
        setContentView(bining.getRoot());
        bining.appBar.title.setText("All users");
        bining.appBar.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        bining.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<LiveUser> options=
                new FirebaseRecyclerOptions.Builder<LiveUser>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("users"),LiveUser.class)
                        .build();
        mainAdapter=new AdminViewUserAdapter(options);
        bining.recyclerView.setAdapter(mainAdapter);
    }
    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mainAdapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
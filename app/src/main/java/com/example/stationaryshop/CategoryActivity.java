package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.adapter.StationaryProductAdapter;
import com.example.model.StationaryProduct;
import com.example.stationaryshop.databinding.ActivityCategoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    StationaryProductAdapter stationaryProductAdapter;
    ArrayList<StationaryProduct> stationaryProducts = new ArrayList<>();
    ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_category);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(CategoryActivity.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.appBar.backBtn.setOnClickListener(v -> onBackPressed());
        String category_name = getIntent().getStringExtra("category_name");
        binding.appBar.title.setText(category_name);
        stationaryProductAdapter = new StationaryProductAdapter(this, stationaryProducts);
        binding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.categoryRecyclerView.setAdapter(stationaryProductAdapter);
        binding.categoryRecyclerView.setHasFixedSize(true);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void onStart() {
        super.onStart();
        String category_name = getIntent().getStringExtra("category_name");
        switch (category_name) {
            case "Painter":
                getDetails("painter");
                break;
            case "Student":
                getDetails("student");
                break;
            case "Business":
                getDetails("business");
                break;
            case "Architecture":
                getDetails("architecture");
                break;
            case "Designer":
                getDetails("designer");
                break;
            case "Gift":
                getDetails("gift");
                break;

            default:
                getDetails("other");
                break;
        }
    }
    private void getDetails(String key) {
        Query query1 = FirebaseDatabase.getInstance().getReference().child("category").child(key).child("data");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                progressDialog.dismiss();
                stationaryProducts.clear();
                if (dataSnapshot1.exists()) {
                    for (DataSnapshot StationaryProductsnapshot1 : dataSnapshot1.getChildren()) {
                        StationaryProduct StationaryProduct = StationaryProductsnapshot1.getValue(StationaryProduct.class);
                        stationaryProducts.add(StationaryProduct);
                    }
                    stationaryProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.adapter.StationaryProductAdapter;
import com.example.adminAdapter.AdminViewProductAdapter;
import com.example.adminAdapter.AdminViewProductCategoryAdapter;
import com.example.model.StationaryProduct;
import com.example.stationaryshop.databinding.ActivityViewAllProductBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class ViewAllProductActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    AdminViewProductAdapter mainAdapter;
    AdminViewProductCategoryAdapter categoryAdapter;
    ActivityViewAllProductBinding binding;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewAllProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences=this.getSharedPreferences("doctro",MODE_PRIVATE);
        String categoryname=sharedPreferences.getString("categoryname","");


        //setContentView(R.layout.activity_view_all_product);
        binding.appbar.title.setText("All Products");
        binding.appbar.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<StationaryProduct>options=
                new FirebaseRecyclerOptions.Builder<StationaryProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("allproduct"), StationaryProduct.class )
                        .build();
        mainAdapter=new AdminViewProductAdapter(options);
        binding.recyclerView.setAdapter(mainAdapter);
        FirebaseRecyclerOptions<StationaryProduct>options1=
                new FirebaseRecyclerOptions.Builder<StationaryProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("catgoryname"), StationaryProduct.class )
                        .build();
        categoryAdapter=new AdminViewProductCategoryAdapter(options1);
        binding.recyclerViewcategory.setAdapter(categoryAdapter);
        binding.addProduct.setOnClickListener(view -> {
            startActivity(new Intent(ViewAllProductActivity.this, AddProduct.class));
        });
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
    public void iv1(View v){
        Intent i=new Intent(getApplicationContext(),AddProduct.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
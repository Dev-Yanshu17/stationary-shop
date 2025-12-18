package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.adapter.StationaryProductAdapter;
import com.example.model.Category;
import com.example.model.StationaryProduct;
import com.example.stationaryshop.databinding.ActivityAllProductPageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class AllProductPage extends AppCompatActivity {
    ArrayList<StationaryProduct>stationaryProducts=new ArrayList<>();
    ProgressDialog progressDialog;
    StationaryProductAdapter stationaryProductAdapter;
    ActivityAllProductPageBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_all_product_page);
        binding=ActivityAllProductPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBar.backBtn.setOnClickListener(view -> onBackPressed());
        binding.appBar.title.setText("All Products");

        stationaryProductAdapter=new StationaryProductAdapter(this,stationaryProducts);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setAdapter(stationaryProductAdapter);
        binding.recyclerView.setHasFixedSize(true);
        progressDialog=new ProgressDialog(AllProductPage.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.searchBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {filter(editable.toString());

            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        Query query1= FirebaseDatabase.getInstance().getReference("allproduct");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                progressDialog.dismiss();
                stationaryProducts.clear();
                if(dataSnapshot1.exists()){
                    for (DataSnapshot StationaryProductsnapshot1 : dataSnapshot1.getChildren()){
                        StationaryProduct StationaryProduct=StationaryProductsnapshot1.getValue(StationaryProduct.class);
                        stationaryProducts.add(StationaryProduct);

                    }
                    stationaryProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void filter(String text) {
        ArrayList<StationaryProduct> newList = new ArrayList<>();
        for (StationaryProduct item : stationaryProducts) {
            // Search by product name
            if (item.getPname().toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                newList.add(item);
            }
            // Search by description
            else if (item.getPdescription().toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                newList.add(item);
            }

            // Search by price
            else if (String.valueOf(item.getPprice()).toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                newList.add(item);
            }
        }
        stationaryProductAdapter.setFilter(newList);

        // Filter categories

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
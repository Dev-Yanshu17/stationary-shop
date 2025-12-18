package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.database.SqliteDatabase;
import com.example.model.StationaryProduct;
import com.example.model.UserOrder;
import com.example.stationaryshop.databinding.ActivityStationaryCartBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StationaryCartActivity extends AppCompatActivity {
    private int quantity=1;
    private  double pricePerUnit=0;
    public static final String TAG = "StationaryCartActivity";
    private SqliteDatabase mDatabase;
    private SharedPreferences sharedPreferences;
    ActivityStationaryCartBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_stationary_cart);
        binding=ActivityStationaryCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBar.title.setText("Address");
        binding.appBar.backBtn.setOnClickListener(view -> onBackPressed());
        sharedPreferences=this.getSharedPreferences("doctro",MODE_PRIVATE);
        String pimage=getIntent().getStringExtra("pimage");
        String pname=getIntent().getStringExtra("pname");
        String pprice=getIntent().getStringExtra("pprice");
        pricePerUnit=Double.parseDouble(pprice);
        binding.confirmButton.setText("MRP : ₹ "+pricePerUnit);

        binding.stationaryProductName.setText("Product Name : " + pname);
        Glide.with(this).load(pimage).into(binding.stationaryProductImage);
        binding.stationaryProductprice.setText("MRP : ₹ " + pprice);

        binding.plus.setOnClickListener(v -> {
            incrementQuantity();
        });

        binding.minus.setOnClickListener(v -> {
            decrementQuantity();
        });

        binding.confirmButton.setOnClickListener(v -> {
            if (isVerify()){
                if (binding.quantity.getText().toString().equals("0")){
                    Toast.makeText(this,"Please Buy Minimum One Product.",Toast.LENGTH_SHORT).show();
                }else {
                    String firstname = binding.firstName.getText().toString();
                    String lastname = binding.lastName.getText().toString();
                    String mobile = binding.mobile.getText().toString();
                    String zipCode = binding.zipCode.getText().toString();
                    String address = binding.address.getText().toString();
                    //String addressLine2 = binding.addressLine2.getText().toString();
                    String landmark = binding.landmark.getText().toString();
                    String productImage = pimage;
                    String productName = pname;
                    String productPrice = pprice;
                    String productQuantity = binding.quantity.getText().toString();
                    String productTotalPrice = String.valueOf(pricePerUnit);

                    Log.e(TAG, "firstname: " + firstname);
                    Log.e(TAG, "lastname: " + lastname);
                    Log.e(TAG, "mobile: " + mobile);
                    Log.e(TAG, "zipCode: " + zipCode);
                    Log.e(TAG, "address: " + address);
                    //Log.e(TAG, "addressLine2: " + addressLine2);
                    Log.e(TAG, "landmark: " + landmark);

                    Log.e(TAG, "productImage: " + productImage);
                    Log.e(TAG, "productName: " + productName);
                    Log.e(TAG, "productPrice: " + productPrice);
                    Log.e(TAG, "productQuantity: " + productQuantity);
                    Log.e(TAG, "productTotalPrice: " + productTotalPrice);
                   mDatabase = new SqliteDatabase(this);
                    UserOrder userOrder = new UserOrder(firstname, lastname, mobile, zipCode, address , landmark, productImage, productName, productPrice, productQuantity, productTotalPrice);
                    mDatabase.addUserOrder(userOrder);
                    Map<String, Object> map = new HashMap<>();
                    map.put("firstname", firstname);
                    map.put("lastname", lastname);
                    map.put("mobile", mobile);
                    map.put("zipCode", zipCode);
                    map.put("address", address);
                   // map.put("addressLine2", addressLine2);
                    map.put("landmark", landmark);
                    map.put("productImage", productImage);
                    map.put("productName", productName);
                    map.put("productPrice", productPrice);
                    map.put("productQuantity", productQuantity);
                    map.put("productTotalPrice", productTotalPrice);

                    FirebaseDatabase.getInstance().getReference().child("allorder").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                    startActivity(new Intent(StationaryCartActivity.this, cashonlivry.class));
                }
            }else {
                Toast.makeText(this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    boolean isVerify() {
        if (binding.firstName.getText().toString().trim().isEmpty()) {
            binding.firstName.setError("First Name is empty");
            binding.firstName.requestFocus();
            return false;
        }
        if (binding.lastName.getText().toString().trim().isEmpty()) {
            binding.lastName.setError("Last Name is empty");
            binding.lastName.requestFocus();
            return false;
        }
        if (binding.mobile.getText().toString().trim().isEmpty()) {
            binding.mobile.setError("Mobile Number is empty");
            binding.mobile.requestFocus();
            return false;
        }
        if (!(binding.mobile.length() == 10)) {
            binding.mobile.setError("Length of the Mobile Number should be Minimum 10");
            binding.mobile.requestFocus();
            return false;
        }
        if (binding.zipCode.getText().toString().trim().isEmpty()) {
            binding.zipCode.setError("Zip Code is empty");
            binding.zipCode.requestFocus();
            return false;
        }
        if (!(binding.zipCode.length() == 6)) {
            binding.zipCode.setError("Length of the Zip Code should be Minimum 6");
            binding.zipCode.requestFocus();
            return false;
        }
        if (binding.address.getText().toString().trim().isEmpty()) {
            binding.address.setError("Address Name is empty");
            binding.address.requestFocus();
            return false;
        }
        if (binding.landmark.getText().toString().trim().isEmpty()) {
            binding.landmark.setError("Landmark Name is empty");
            binding.landmark.requestFocus();
            return false;
        }
        return true;
    }
    public void incrementQuantity() {
        quantity++;
        updateQuantityText();
        updateTotalPrice();
    }

    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
            updateQuantityText();
            updateTotalPrice();
        } else {
        }
    }

    private void updateQuantityText() {
        binding.quantity.setText(String.valueOf(quantity));
    }

    private void updateTotalPrice() {
        double total = quantity * pricePerUnit;
        Log.e(TAG, "updateTotalPrice: " + total);
        binding.confirmButton.setText("MRP: ₹" + String.format("%.2f", total));
    }
}
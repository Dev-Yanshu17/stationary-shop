package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.stationaryshop.databinding.ActivityAdminOrderDetailsBinding;

public class AdminOrderDetailsActivity extends AppCompatActivity {
    ActivityAdminOrderDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_admin_order_details);

        binding=ActivityAdminOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBar.title.setText("Order Details");
        binding.appBar.backBtn.setOnClickListener(v -> onBackPressed());

        String stationaryProductImage=getIntent().getStringExtra("stationaryProductImage");
        String stationaryProductName=getIntent().getStringExtra("stationaryProductName");
        String stationaryProductPrice=getIntent().getStringExtra("stationaryProductPrice");
        String user_first_name=getIntent().getStringExtra("user_first_name");
        String user_last_name=getIntent().getStringExtra("user_last_name");
        String user_mobile_number=getIntent().getStringExtra("user_mobile_number");
        String user_zip_code=getIntent().getStringExtra("user_zip_code");
        String user_address=getIntent().getStringExtra("user_address");
        String user_landmark=getIntent().getStringExtra("user_landmark");
        String user_product_quantity=getIntent().getStringExtra("user_product_quantity");
        String user_product_price=getIntent().getStringExtra("user_product_price");
        String user_product_price_total=getIntent().getStringExtra("user_product_price_total");

        Glide.with(this).load(stationaryProductImage).into(binding.stationaryProductImage);
        binding.stationaryProductName.setText(stationaryProductName);
        binding.stationaryProductprice.setText("MRP :  ₹"+ stationaryProductPrice);
        binding.userFirstName.setText("First Name :" + user_first_name);
        binding.userLastName.setText("Last Name :" + user_last_name);
        binding.userMobileNumber.setText("Mobile Number :-" + user_mobile_number);
        binding.userZipCode.setText("Zip Code :-" + user_zip_code);
        binding.userAddress.setText("Address :-" + user_address);
        binding.userLandmark.setText("Landmark :-" + user_landmark);
        binding.userProductQuantity.setText("Product Quantity :-" + user_product_quantity);
        binding.userProductPrice.setText("Product Price :-" + user_product_price);
        binding.userProductPriceTotal.setText("Product Price Total :-" + user_product_price_total);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
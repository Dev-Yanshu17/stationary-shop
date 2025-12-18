package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.stationaryshop.databinding.ActivityThankyouBinding;

public class thankyou extends AppCompatActivity {
ActivityThankyouBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThankyouBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set a flag indicating that an order has been placed
        SharedPreferences.Editor editor = getSharedPreferences("order_status", MODE_PRIVATE).edit();
        editor.putBoolean("order_placed", true);
        editor.apply();
       /* setContentView(R.layout.activity_thankyou);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(thankyou.this,HomePage.class);
                startActivity(intent);
                finish();

            }
        },2000);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }
}
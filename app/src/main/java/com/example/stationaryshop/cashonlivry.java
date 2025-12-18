package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.stationaryshop.databinding.ActivityCashonlivryBinding;
import com.example.stationaryshop.databinding.ActivityLoginactivityBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class cashonlivry extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashonlivry);

    }
    public void btncash(View v){
        Intent i= new Intent(getApplicationContext(),thankyou.class);
        startActivity(i);
    }

    public void razorpay(View v){
        Intent i= new Intent(getApplicationContext(),get_payment_amount.class);
        startActivity(i);
    }
}
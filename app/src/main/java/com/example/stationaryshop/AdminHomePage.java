package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
    }
    public void tv1(View v){
        Intent i=new Intent(getApplicationContext(),ViewReviewActivity.class);
        startActivity(i);
    }
    public void tv2(View v){
        Intent i=new Intent(getApplicationContext(),ViewOrderActivity.class);
        startActivity(i);
    }
    public void tv3(View v){
        Intent i=new Intent(getApplicationContext(),ViewAllProductActivity.class);
        startActivity(i);
    }
    public void tv4(View v){
        Intent i=new Intent(getApplicationContext(),ViewAllUserActivity.class);
        startActivity(i);
    }
    public void tv5(View v){
        Intent i=new Intent(getApplicationContext(),about_us_page.class);
        startActivity(i);
    }
    public void tv6(View v){
        Intent i=new Intent(getApplicationContext(),admin_profile.class);
        startActivity(i);
    }
    public void tv7(View v){
        Intent i=new Intent(getApplicationContext(),view_admin_rate.class);
        startActivity(i);
    }

}
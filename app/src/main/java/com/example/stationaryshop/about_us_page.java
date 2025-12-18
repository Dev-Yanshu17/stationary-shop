package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class about_us_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page);
    }
    public void location(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Skyblue+Stationery+Mart/@21.1491057,72.7734336,17z/data=!3m1!4b1!4m6!3m5!1s0x3be04d8a13199291:0x88730c28ca44dc2b!8m2!3d21.1491057!4d72.7760085!16s%2Fg%2F11g8djtdlg?entry=ttu"));
        startActivity(i);
    }
    public void call_call(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+8780476710"));
        startActivity(i);
    }
    public void instagram(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/skybluestationerymart_official/"));
        startActivity(i);
    }
    public void facebook(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/skybluemart/"));
        startActivity(i);
    }
    public void pinterest(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.pinterest.com/skybluestationerymart/"));
        startActivity(i);
    }
    public void website(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skybluestationerymart.com/"));
        startActivity(i);
    }
    public void call_backsignup(View v){
        Intent i = new Intent(getApplicationContext(), AdminHomePage.class);
        startActivity(i);
    }
}

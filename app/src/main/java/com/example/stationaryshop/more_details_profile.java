package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.os.Bundle;

import com.example.stationaryshop.databinding.ActivityMoreDetailsProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class more_details_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details_profile);


    }
    public void shareapp(View v){
        String appPackageName = getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPreferences = getSharedPreferences("doctro", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("mobile", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        sp.edit().clear().apply();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finishAffinity();
    }
    
    public void call_backsignup(View v){
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }
    public void home(View v){
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
    }
    public void order(View v){
        Intent i = new Intent(getApplicationContext(), OrderActivity.class);
        startActivity(i);
    }
    public void wishlist(View v){
        Intent i = new Intent(getApplicationContext(), WishListActivity.class);
        startActivity(i);
    }
    public void profile(View v){
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }
    public void changepassword(View v){
        Intent i = new Intent(getApplicationContext(), change_password.class);
        startActivity(i);
    }
    public void aboutus(View v){
        Intent i = new Intent(getApplicationContext(), about_us_page_user.class);
        startActivity(i);
    }
    public void rating(View v){
        Intent i = new Intent(getApplicationContext(),rate_app.class);
        startActivity(i);
    }
}
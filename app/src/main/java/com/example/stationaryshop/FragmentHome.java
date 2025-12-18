package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class FragmentHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);
    }
    public void call_backsignup(View v){
        Intent i = new Intent(getApplicationContext(), Loginactivity.class);
        startActivity(i);
    }
}
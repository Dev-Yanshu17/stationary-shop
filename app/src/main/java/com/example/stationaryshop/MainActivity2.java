package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
    public void loginn(View v){
        Intent i=new Intent(getApplicationContext(),Loginactivity.class);
        startActivity(i);
    }
    public void signup(View v){
        Intent i=new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(i);
    }


}
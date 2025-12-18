package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.stationaryshop.databinding.ActivityIntroduction1Binding;
import com.example.stationaryshop.databinding.ActivityIntroduction2Binding;

public class introduction2 extends AppCompatActivity {
    ActivityIntroduction2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_introduction2);
        binding= ActivityIntroduction2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.next.setOnClickListener(v -> {
            startActivity(new Intent(introduction2.this,HomePage.class));
        });
    }
    public void call_backsignup(View v){
        Intent i = new Intent(getApplicationContext(), introduction2.class);
        startActivity(i);
    }
}
package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.stationaryshop.databinding.ActivityIntroduction1Binding;

public class introduction1 extends AppCompatActivity {
    ActivityIntroduction1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_introduction1);
        binding=ActivityIntroduction1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.next.setOnClickListener(v -> {
            startActivity(new Intent(introduction1.this,introduction2.class));
        });
    }
}
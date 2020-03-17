package com.example.cs2001_group10;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.five_layout);
        getSupportActionBar().hide();// 隐藏ActionBar
    }
}

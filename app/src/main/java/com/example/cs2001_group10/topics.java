package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class topics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        Button java_button = (Button) findViewById(R.id.java_question);
        java_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(topics.this,select_Topic.class));
            }
        });
        Button algebra_button = (Button) findViewById(R.id.algebra);
        algebra_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(topics.this,select_Topic.class));
            }
        });
        Button chemistry_button = (Button) findViewById(R.id.chemistry);
        chemistry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(topics.this,select_Topic.class));
            }
        });
        Button back_topics_btn = (Button) findViewById(R.id.back_topics);
        back_topics_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(topics.this,home_screen.class));
            }
        });
    }
}

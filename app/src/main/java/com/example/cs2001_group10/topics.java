package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class topics extends AppCompatActivity {
    String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        final Intent open_Topic_Detail = new Intent(topics.this, select_Topic.class);


        Button b_Java = (Button) findViewById(R.id.b_Java);
        b_Java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = "Java"; //Setting the selected topic
                open_Topic_Detail.putExtra("Topic", topic); // Used to send the selected tpoic to the next screen
                startActivity(open_Topic_Detail);
            }
        });

        Button b_Python = (Button) findViewById(R.id.b_Python);
        b_Python.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = "Python";//Setting the selected topic
                open_Topic_Detail.putExtra("Topic", topic);// Used to send the selected tpoic to the next screen
                startActivity(open_Topic_Detail);
            }
        });

        Button b_Math = (Button) findViewById(R.id.b_Math);
        b_Math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = "Math";//Setting the selected topic
                open_Topic_Detail.putExtra("Topic", topic);// Used to send the selected tpoic to the next screen
                startActivity(open_Topic_Detail);
            }
        });

        Button b_Back = (Button) findViewById(R.id.back_topics);
        b_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(topics.this,home_screen.class));
            }
        });
    }
}

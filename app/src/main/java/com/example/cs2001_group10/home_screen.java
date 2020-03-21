package com.example.cs2001_group10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class home_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

        Button settings_menu = (Button) findViewById(R.id.setting);
        settings_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_screen.this,setting_Menu.class));
            }
        });

        Button select_topics_menu = (Button) findViewById(R.id.topics);
        select_topics_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_screen.this,topics.class));
            }
        });

        Button popup_Test = (Button) findViewById(R.id.popUpTest);
        popup_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), popup_Service.class));
            }
        });

    }
}

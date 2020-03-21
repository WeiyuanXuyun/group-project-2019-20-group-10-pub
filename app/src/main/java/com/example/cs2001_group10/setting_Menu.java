package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class setting_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__menu);

        Button select_app = (Button) findViewById(R.id.select);
        select_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setting_Menu.this,selectapps.class));
            }
        });
        Button timer_menu = (Button) findViewById(R.id.timer);
        timer_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setting_Menu.this,settimer.class));
            }
        });
        Button back_settings_menu = (Button) findViewById(R.id.back_settings_menu);
        back_settings_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setting_Menu.this,home_screen.class));
            }
        });
    }
}

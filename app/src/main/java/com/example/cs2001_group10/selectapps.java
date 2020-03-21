package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectapps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectapp);

        Button enter_btn = (Button) findViewById(R.id.enter_button);//to go back to settings screen after apps have been selected
        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selectapps.this,setting_Menu.class));
            }
        });

        Button backselect = (Button) findViewById(R.id.backselectapp);
        backselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selectapps.this,setting_Menu.class));
            }
        });
    }
}

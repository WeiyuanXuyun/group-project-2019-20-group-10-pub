package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class select_Topic extends AppCompatActivity {

    private static final String TAG = "Select Topic Screen Activity" ;
    ArrayList <String> saved_Topics = new ArrayList<String>();
    Boolean check;
    TextView tv_Example, tv_Description;
    String java_Descript, python_Descript, math_Descript; // Creating the description variables.
    String java_Example, python_Example, math_Example; // Creating the example variables.
    Button b_Select;
    String topic;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__topic);
        Log.d(TAG, "onCreate: Activity Created");

        // Setting the descriptions for each topic.
        java_Descript = "Selecting this topic will make Java related questions pop up. An example is:";
        python_Descript = "Selecting this topic will make Python related questions pop up. An example is:";
        math_Descript = "Selecting this topic will make Math related questions pop up. An example is:";

        check = false;

        Log.d(TAG, "Maths List: " + MainActivity.Maths_List);
        Log.d(TAG, "Java List: " + MainActivity.Java_List);
        Log.d(TAG, "Python List: " + MainActivity.Python_List);

        // Getting an example, to show the user what sort of questions they will the tested on.
        java_Example = MainActivity.Java_List.get(1); // <- can be randomised if wanted.
        python_Example = MainActivity.Python_List.get(1); // <- can be randomised if wanted.
        math_Example = MainActivity.Maths_List.get(1); // <- can be randomised if wanted.

        //Setting the layout ID's
        tv_Example  = findViewById(R.id.tv_Exp);
        tv_Description = findViewById(R.id.tv_Descipt);
        b_Select = findViewById(R.id.b_select);

        //Finding out which topic has been selected.
        if(Objects.equals(getIntent().getStringExtra("Topic"), "Java")){ //Tests to see what was passed from the previous screen.
            tv_Example.setText(java_Example);
            tv_Description.setText(java_Descript);
            topic = "Java";
        }else if(Objects.equals(getIntent().getStringExtra("Topic"), "Python")){
            tv_Example.setText(python_Example);
            tv_Description.setText(python_Descript);
            topic = "Python";
        }else if(Objects.equals(getIntent().getStringExtra("Topic"), "Math")){
            tv_Example.setText(math_Example);
            tv_Description.setText(math_Descript);
            topic = "Math";
        }

        // Checks if topic has been already been selected.
        for (i = 0; i <saved_Topics.size(); i++){
            if (saved_Topics.get(i) == topic){
                b_Select.setText("Selected");
                check = true;
                break;
            }
        }

        //if it has been selected, sets the box to be "Select".
        if (!check){
            b_Select.setText("Select");
        }

        b_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Adds topic to ArrayList based on the text.
                if (b_Select.getText() == "Select"){
                    if(topic == "Java"){
                        b_Select.setText("Selected");
                        saved_Topics.add(topic);
                        Log.d(TAG, "Java Selected: " + saved_Topics);
                    }else if(topic == "Python"){
                        b_Select.setText("Selected");
                        saved_Topics.add(topic);
                        Log.d(TAG, "Python Selected: " + saved_Topics);
                    }else if(topic == "Math"){
                        b_Select.setText("Selected");
                        saved_Topics.add(topic);
                        Log.d(TAG, "Maths Selected: " + saved_Topics);
                    }
                } else { // if the text dose not = 'Select' it removes the topic
                    for (i = 0; i<saved_Topics.size(); i++){
                        if (saved_Topics.get(i) == topic){
                            saved_Topics.remove(i);
                            b_Select.setText("Select");
                        }
                    }
                }
                }
        });

        Button back_select_topics = (Button) findViewById(R.id.back_select_topic);
        back_select_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(select_Topic.this,topics.class));
            }
        });

    }
}

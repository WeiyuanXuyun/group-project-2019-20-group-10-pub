package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class select_Topic extends AppCompatActivity {

    private static final String TAG = "Select Topic Screen Activity" ;
    Boolean check;
    TextView tv_Example, tv_Description;
    String java_Descript, python_Descript, math_Descript; // Creating the description variables.
    String java_Example, python_Example, math_Example; // Creating the example variables.
    Button b_Select;
    String topic;
    int i;
    private TextView Topic_Name_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__topic);



        Log.d(TAG, "onCreate: Activity Created");
        Log.d(TAG, "onCreate: Saved Topic Array " + MainActivity.saved_Topics);

        // Setting the descriptions for each topic.
        java_Descript = "Selecting this topic will make Java related questions pop up. An example is:";
        python_Descript = "Selecting this topic will make Python related questions pop up. An example is:";
        math_Descript = "Selecting this topic will make Math related questions pop up. An example is:";


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
        }else if(Objects.equals(getIntent().getStringExtra("Topic"), "Maths")){
            tv_Example.setText(math_Example);
            tv_Description.setText(math_Descript);
            topic = "Maths";
        }

        Topic_Name_Text  = findViewById(R.id.Topic_Name);
        Topic_Name_Text.setText("Topic: " + topic);


        b_Select.setText("Select");

        for (int i = 0; i < MainActivity.saved_Topics.size(); i ++) {
            if (topic.equals(MainActivity.saved_Topics.get(i))) {
                b_Select.setText("Selected");
            }
        }

        b_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Adds topic to ArrayList based on the text.
                if (b_Select.getText() == "Select"){

                    b_Select.setText("Selected");
                    Update_Topic(topic, "1");
                } else { // if the text dose not = 'Select' it removes the topic
                    Update_Topic(topic, "0");
                    //MainActivity.saved_Topics.remove(i);  // change to remove from dataabase
                    b_Select.setText("Select");
                    check = true;
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

    private void Update_Topic(final String topic, final String value) {
        Log.d(TAG, "Update Topic: Accessed");
        Log.d(TAG, "Update_Topic: Email " + MainActivity.email);
        Log.d(TAG, "Update_Topic: topic " + topic);
        Log.d(TAG, "Update_Topic: Value " + value);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_UPDATE_TOPICS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: Call " +response);
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jsonObject);
                            String Value = jsonObject.getString("value");

                            if (Value.equals("0")) {
                                Toast.makeText(select_Topic.this, "Topic Update Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(select_Topic.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("topic", topic);
                params.put("email", MainActivity.email);
                params.put("value", value);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

}

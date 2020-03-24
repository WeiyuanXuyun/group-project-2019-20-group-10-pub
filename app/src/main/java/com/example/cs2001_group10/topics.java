package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class topics extends AppCompatActivity {
    String topic;
    private static final String TAG = "Topic Buttons Screen Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        final Intent open_Topic_Detail = new Intent(topics.this, select_Topic.class);

        MainActivity.saved_Topics.clear();
        Get_Topics(MainActivity.email, Api.URL_GET_TOPICS);


        Button b_Java = (Button) findViewById(R.id.b_Java);
        b_Java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = "Java"; //Setting the selected topic
                open_Topic_Detail.putExtra("Topic", topic); // Used to send the selected tpoic to the next screen
                SystemClock.sleep(500);
                startActivity(open_Topic_Detail);
            }
        });

        Button b_Python = (Button) findViewById(R.id.b_Python);
        b_Python.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = "Python";//Setting the selected topic
                open_Topic_Detail.putExtra("Topic", topic);// Used to send the selected tpoic to the next screen
                SystemClock.sleep(500);
                startActivity(open_Topic_Detail);
            }
        });

        Button b_Math = (Button) findViewById(R.id.b_Math);
        b_Math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = "Maths";//Setting the selected topic
                open_Topic_Detail.putExtra("Topic", topic);// Used to send the selected tpoic to the next screen
                SystemClock.sleep(500);
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


    public void Get_Topics(final String email, String URL) {
        Log.d(TAG, "Get Answers: Accessed");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: Reponse " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jsonObject);
                            JSONArray Array = jsonObject.getJSONArray("topics");

                            int maths_result = Array.getJSONObject(0).getInt("maths");
                            int java_result = Array.getJSONObject(0).getInt("java");
                            int python_result = Array.getJSONObject(0).getInt("python");

                            if (maths_result == 1) {
                                MainActivity.saved_Topics.add("Maths");
                            }
                            if (java_result == 1) {
                                MainActivity.saved_Topics.add("Java");
                            }
                            if (python_result == 1) {
                                MainActivity.saved_Topics.add("Python");
                            }

                            Log.d(TAG, "onResponse: maths value: " + maths_result + " java value: " + java_result + " python value: " + python_result);
                            Log.d(TAG, "onResponse: Saved Topics" + MainActivity.saved_Topics);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}

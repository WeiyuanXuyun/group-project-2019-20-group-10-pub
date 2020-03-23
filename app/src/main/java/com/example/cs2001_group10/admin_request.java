package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class admin_request extends AppCompatActivity {

    private static final String TAG = "Request Activity" ;
    public static String Array_Name;
    ArrayList<String> Questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request);

        Log.d(TAG, "onCreate: Work");

        Button maths_button = findViewById(R.id.maths_button);
        Button java_button = findViewById(R.id.java_button);
        Button python_button = findViewById(R.id.python_button);

        maths_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "maths button Working ");
                Array_Name = "maths_questions";
                Request(Api.URL_MATHS_REQUEST, Array_Name);

            }
        });

        java_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "java button Working ");
                Array_Name = "java_questions";
                Request(Api.URL_JAVA_REQUEST, Array_Name);

            }
        });

        python_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "python button Working ");
                Array_Name = "python_questions";
                Request(Api.URL_PYTHON_REQUEST, Array_Name);

            }
        });
    }

    private void Request(String URL, final String Array_Name) {
        Questions.clear();
        Log.d(TAG, "Request: Accessed");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray(Array_Name);
                            Log.d(TAG, "onResponse: " +jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject questions = jsonArray.getJSONObject(i);
                                String question = questions.getString("question");

                                Questions.add(question);
                            }
                            Log.d(TAG, "onResponse: " + Questions);
                            initQuestionsView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void initQuestionsView() {
        Intent intent = new Intent(this, admin_questions.class);
        intent.putExtra("questions", Questions);
        startActivity(intent);
    }

}

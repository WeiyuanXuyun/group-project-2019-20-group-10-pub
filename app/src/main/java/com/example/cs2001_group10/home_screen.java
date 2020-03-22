package com.example.cs2001_group10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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


public class home_screen extends AppCompatActivity {

    private static final String TAG = "Home Screen Activity" ;
    ArrayList<String> Questions = new ArrayList<>();
    public static ArrayList<String> Maths_List = new ArrayList<>();
    public static ArrayList<String> Java_List = new ArrayList<>();
    //public static ArrayList<String> Random_List = new ArrayList<>();
    public static String Array_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

        Array_Name = "maths_questions";
        Request(Api.URL_MATHS_REQUEST, Array_Name);

        //Array_Name = "java_questions";
        //Request(Api.URL_JAVA_REQUEST, Array_Name);


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
                Intent popup = new Intent(getBaseContext(), questions.class);
                startActivity(popup);
            }
        });

    }


    private void Request(String URL, final String Array_Name) {
        Log.d(TAG, "Request: Accessed");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray(Array_Name);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject questions = jsonArray.getJSONObject(i);
                                String question = questions.getString("question");

                                Questions.add(question);
                            }
                            Log.d(TAG, "onResponse: " + Questions);
                            if (Array_Name == "maths_questions") {
                                Maths_List = (ArrayList<String>) Questions.clone();
                                Questions.clear();
                            }

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
}

package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class questions extends AppCompatActivity {
    private static final String TAG = "Questions Screen Activity" ;
    ArrayList<String> maths_list = new ArrayList<>();
    private TextView answer_one, answer_two, correct_answer;
    private String ans_result, ans_two_result, correct_ans_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Log.d(TAG, "onCreate: " + home_screen.Maths_List);


        int Topic = 1;

        if (Topic == 1) {
            String Question = Random_Question(home_screen.Maths_List);
            Log.d(TAG, "Topic Question: " + Question);
            GetAnswers(Question, Api.URL_MATHS_ANSWERS_REQUEST, "maths_questions");
        }

    }


    // method that gets random question

    private String Random_Question(ArrayList<String> maths_List) {
        //Log.d(TAG, "Random_Question: " + home_screen.Maths_List.get(1));

        return  home_screen.Maths_List.get(1);
    }

    private void GetAnswers(final String question, String URL, final String Array_Name) {
        Log.d(TAG, "Get Answers: Accessed");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jsonObject);
                            JSONArray Array = jsonObject.getJSONArray(Array_Name);


                            ans_result = Array.getJSONObject(0).getString("answer");
                            ans_two_result = Array.getJSONObject(0).getString("answer_2");
                            correct_ans_result = Array.getJSONObject(0).getString("correct_answer");

                            Log.d(TAG, "onResponse: answers" + ans_result + " " + ans_two_result + " " + correct_ans_result);

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
                params.put("question", question);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }




}

package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class questions extends AppCompatActivity {
    private static final String TAG = "Questions Screen Activity" ;
    ArrayList<String> maths_Questions = new ArrayList<>();
    private TextView question;
    private String ans_result, ans_two_result, correct_ans_result;
    private Button b_ans1, b_ans2, b_ans3;
    private Boolean first_Time =true;
    private int q_Num, current_Question = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Log.d(TAG, "onCreate: " + home_screen.Maths_List);
        b_ans1 = findViewById(R.id.b_A1);
        b_ans2 = findViewById(R.id.b_A2);
        b_ans3 = findViewById(R.id.b_A3);
        question = findViewById(R.id.t_Question);



        if (first_Time == true){
            Log.d(TAG, "onCreate: boolean check");
            for (int i = 0; i <5; i++){
                q_Num = i; //Add random num 5 <-
                maths_Questions.add(Random_Question(home_screen.Maths_List, q_Num));
            }
            first_Time = false;
        } else if (current_Question == 4){
            first_Time = true;
            current_Question = 0;
        }

            Log.d(TAG, "Topic Question: " + maths_Questions);


            question.setText(home_screen.Maths_List.get(current_Question));
            GetAnswers(maths_Questions.get(current_Question), Api.URL_MATHS_ANSWERS_REQUEST, "maths_questions");
            current_Question++;

        b_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(questions.this, "incorrect", Toast.LENGTH_SHORT).show();
                next_Question();
            }
        });

        b_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(questions.this, "incorrect", Toast.LENGTH_SHORT).show();
                next_Question();
            }
        });

        b_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(questions.this, "correct", Toast.LENGTH_SHORT).show();
                next_Question();
            }
        });
    }

    public void next_Question() {
        question.setText(home_screen.Maths_List.get(current_Question));
        GetAnswers(maths_Questions.get(current_Question), Api.URL_MATHS_ANSWERS_REQUEST, "maths_questions");
        current_Question++;
    }

    // method that gets random question

    private String Random_Question(ArrayList<String> maths_List, int q_Num) {
        Log.d(TAG, "Random_Question: " + home_screen.Maths_List.get(q_Num));
        return  home_screen.Maths_List.get(q_Num);
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

                            b_ans1.setText("First Answer: " + Array.getJSONObject(0).getString("answer"));
                            ans_result = Array.getJSONObject(0).getString("answer");
                            b_ans2.setText("Second Answer: " + Array.getJSONObject(0).getString("answer_2"));
                            ans_two_result = Array.getJSONObject(0).getString("answer_2");
                            b_ans3.setText("Third Answer: " + Array.getJSONObject(0).getString("correct_answer"));
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

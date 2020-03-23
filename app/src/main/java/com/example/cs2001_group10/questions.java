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
import java.util.Random;

public class questions extends AppCompatActivity {
    private static final String TAG = "Questions Screen Activity" ;
    ArrayList<String> maths_Questions = new ArrayList<>();
    ArrayList<String> list_Topics = new ArrayList<>();
    private TextView question;
    private String ans_result, ans_two_result, correct_ans_result;
    private Button b_ans1, b_ans2, b_ans3;
    private Boolean first_Time =true;
    private int q_Num, current_Question = 0;
    Random rand_Int = new Random();
    int i, rand_Topic, rand_Question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Log.d(TAG, "onCreate: " + MainActivity.Maths_List);

        //Sets all the buttons and TextViewws to variables.
        b_ans1 = findViewById(R.id.b_A1);
        b_ans2 = findViewById(R.id.b_A2);
        b_ans3 = findViewById(R.id.b_A3);
        question = findViewById(R.id.t_Question);

        for(i = 0; i < 5; i++){

            //Gets a random ints
            rand_Topic = rand_Int.nextInt(MainActivity.saved_Topics.size());
            rand_Question = rand_Int.nextInt(MainActivity.Maths_List.size());

            list_Topics.add(MainActivity.saved_Topics.get(rand_Topic));

            if (list_Topics.get(i) == "Java"){
                maths_Questions.add(MainActivity.Java_List.get(rand_Question));
            }else if (list_Topics.get(i) == "Maths"){
                maths_Questions.add(MainActivity.Maths_List.get(rand_Question));
            }else if (list_Topics.get(i) == "Python"){
                maths_Questions.add(MainActivity.Python_List.get(rand_Question));
            }

        }

        Log.d(TAG, "Topic Question: " + list_Topics);

        //* Adds questions to an Array.
        //for (int i = 0; i <5; i++){
         //   q_Num = i; //Add random num 5 <-
        //    maths_Questions.add(MainActivity.Maths_List.get(q_Num)); //Gets a random element from an Array.
        //}

        Log.d(TAG, "Topic Question: " + maths_Questions);

        // Displays the first question and answers.
        next_Question();

        b_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_Question == 4){
                    startActivity(new Intent(questions.this,home_screen.class));
                }
                Toast.makeText(questions.this, "incorrect", Toast.LENGTH_SHORT).show();
                next_Question();
            }
        });

        b_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current_Question == 4){
                    startActivity(new Intent(questions.this,home_screen.class));
                }

                Toast.makeText(questions.this, "incorrect", Toast.LENGTH_SHORT).show();
                next_Question();
            }
        });

        b_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current_Question == 4){
                    startActivity(new Intent(questions.this,home_screen.class));
                }
                Toast.makeText(questions.this, "correct", Toast.LENGTH_SHORT).show();
                next_Question();
            }
        });
    }

    //Used to get and display the questions, and prepares to get the next.
    public void next_Question() {
        question.setText(maths_Questions.get(current_Question)); //Displays the current question
        // if statment
        if (list_Topics.get(current_Question) == "Java") {
            GetAnswers(maths_Questions.get(current_Question), Api.URL_JAVA_ANSWERS_REQUEST, "java_questions");// Runs method to display answers for java
        } else if(list_Topics.get(current_Question) == "Python") {
            GetAnswers(maths_Questions.get(current_Question), Api.URL_PYTHON_ANSWERS_REQUEST, "python_questions");// Runs method to display answers for python
        } else if(list_Topics.get(current_Question) == "Maths") {
            GetAnswers(maths_Questions.get(current_Question), Api.URL_MATHS_ANSWERS_REQUEST, "maths_questions");// Runs method to display maths
        }

        current_Question++;
    }

    //Method to pull the answers and display them, based on the current question.
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

                            b_ans1.setText(Array.getJSONObject(0).getString("answer"));
                            ans_result = Array.getJSONObject(0).getString("answer");
                            b_ans2.setText(Array.getJSONObject(0).getString("answer_2"));
                            ans_two_result = Array.getJSONObject(0).getString("answer_2");
                            b_ans3.setText(Array.getJSONObject(0).getString("correct_answer"));
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

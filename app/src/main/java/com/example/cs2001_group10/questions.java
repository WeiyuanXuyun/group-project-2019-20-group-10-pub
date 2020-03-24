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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class questions extends AppCompatActivity {

    private static final String TAG = "Questions Screen Activity" ;
    ArrayList<String> maths_Questions = new ArrayList<>();
    ArrayList<String> list_Topics = new ArrayList<>();
    ArrayList<Button> rand_Buttons = new ArrayList<>();
    ArrayList<String> rand_Results = new ArrayList<>();

    private TextView question;
    private String ans_result, ans_two_result, correct_ans_result;
    private Button b_ans1, b_ans2, b_ans3, b_sco;
    private int current_Question = 0;
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
        b_sco = findViewById(R.id.b_score);
        question = findViewById(R.id.t_Question);

        if(MainActivity.score >=5){
            b_sco.setText("Skip Questions");
        }else{
            b_sco.setText("Need more points");
        }


        for(i = 0; i < 5; i++){ // change number for more or less questions being asked.

            //Gets a random ints
            rand_Topic = rand_Int.nextInt(MainActivity.saved_Topics.size());
            rand_Question = rand_Int.nextInt(MainActivity.Maths_List.size());

            // gets a random topic, and saves it to a local arraylist.
            list_Topics.add(MainActivity.saved_Topics.get(rand_Topic));

            // depending on topic, gets a random question.
            if (list_Topics.get(i) == "Java"){
                maths_Questions.add(MainActivity.Java_List.get(rand_Question));
            }else if (list_Topics.get(i) == "Maths"){
                maths_Questions.add(MainActivity.Maths_List.get(rand_Question));
            }else if (list_Topics.get(i) == "Python"){
                maths_Questions.add(MainActivity.Python_List.get(rand_Question));
            }

        }

        Log.d(TAG, "Topic Question: " + list_Topics);

        Log.d(TAG, "Topic Question: " + maths_Questions);

        // Displays the first question and answers.
        next_Question();

        b_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ans_Check(b_ans1);
            }
        });

        b_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ans_Check(b_ans2);
            }
        });

        b_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ans_Check(b_ans3);
            }
        });

        b_sco.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(MainActivity.score>=5){
                    MainActivity.score -= 5;
                    Toast.makeText(questions.this,"Questions skipped, 5 points used", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(questions.this,home_screen.class));

                }
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

                            //Gets answers from specified table and saves it to variables
                            ans_result = Array.getJSONObject(0).getString("answer");
                            ans_two_result = Array.getJSONObject(0).getString("answer_2");
                            correct_ans_result = Array.getJSONObject(0).getString("correct_answer");

                            //Makes index:0-2 equal to answer buttons and then shuffles the arrayList.
                            rand_Buttons.clear();
                            rand_Buttons.add(0, b_ans1);
                            rand_Buttons.add(1,b_ans2);
                            rand_Buttons.add(2, b_ans3);
                            Collections.shuffle(rand_Buttons);

                            //Adds results to an arrayList and then shuffles it.
                            rand_Results.clear();
                            rand_Results.add(0, ans_result);
                            rand_Results.add(1, ans_two_result);
                            rand_Results.add(2, correct_ans_result);
                            Collections.shuffle((rand_Results));

                            //Sets the random sequence of buttons text to equal the random sequence of results
                            for(i = 0; i <3; i++){
                                rand_Buttons.get(i).setText(rand_Results.get(i));
                            }

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

    //Check to see if the selected answer is correct.
    private void ans_Check(Button ans_Button) {

        if(MainActivity.score >=5){
            b_sco.setText("Skip Questions");
        }else{
            b_sco.setText("Need more points");
        }

        if(ans_Button.getText() == correct_ans_result){
            Toast.makeText(questions.this, "Correct", Toast.LENGTH_SHORT).show();
            MainActivity.score++;
        } else{
            Toast.makeText(questions.this, "incorrect", Toast.LENGTH_SHORT).show();
        }
        if(current_Question == 4){
            startActivity(new Intent(questions.this,home_screen.class));
        } else{
            next_Question();
        }
    }
}

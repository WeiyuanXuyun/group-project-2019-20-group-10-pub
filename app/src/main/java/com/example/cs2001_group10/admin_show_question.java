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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class admin_show_question extends AppCompatActivity {

    private static final String TAG = "Show Question" ;
    private TextView answer_one, answer_two, correct_answer;
    private String ans_result, ans_two_result, correct_ans_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_question);

        final Intent intent = getIntent();

        final TextView question = findViewById(R.id.question_id);

        answer_one = findViewById(R.id.Answer_One);
        answer_two = findViewById(R.id.Answer_Two);
        correct_answer = findViewById(R.id.Correct_Answer);

        final String question_result = intent.getStringExtra("question");
        Log.d(TAG, "onCreate: " + admin_request.Array_Name);

        question.setText(intent.getStringExtra("question"));
        if (Objects.equals(admin_request.Array_Name, "maths_questions")) {
            Log.d(TAG, "onCreate: WORKSSSS");

            GetAnswers(intent.getStringExtra("question"), Api.URL_MATHS_ANSWERS_REQUEST);

        }

        if (Objects.equals(admin_request.Array_Name, "java_questions")) {
            Log.d(TAG, "onCreate: WORKSSSS");

            GetAnswers(intent.getStringExtra("question"), Api.URL_JAVA_ANSWERS_REQUEST);

        }

        if (Objects.equals(admin_request.Array_Name, "python_questions")) {
            Log.d(TAG, "onCreate: WORKSSSS");

            GetAnswers(intent.getStringExtra("question"), Api.URL_PYTHON_ANSWERS_REQUEST);

        }

        Button Update = findViewById(R.id.update);
        final Button Delete = findViewById(R.id.delete);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Update Working ");

                Intent  intent = new Intent(admin_show_question.this, admin_update_question.class);
                intent.putExtra("question", question_result);
                intent.putExtra("answer", ans_result);
                intent.putExtra("answer_two", ans_two_result);
                intent.putExtra("correct_answer", correct_ans_result);
                startActivity(intent);

            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Delete Working ");
                Delete(intent.getStringExtra("question"), admin_request.Array_Name);
            }
        });


    }


    private void GetAnswers(final String question, String URL) {
        Log.d(TAG, "Get Answers: Accessed");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jsonObject);
                            JSONArray Array = jsonObject.getJSONArray(admin_request.Array_Name);

                            answer_one.setText("First Answer: " + Array.getJSONObject(0).getString("answer"));
                            ans_result = Array.getJSONObject(0).getString("answer");
                            answer_two.setText("Second Answer: " + Array.getJSONObject(0).getString("answer_2"));
                            ans_two_result = Array.getJSONObject(0).getString("answer_2");
                            correct_answer.setText("Correct Answer: "+ Array.getJSONObject(0).getString("correct_answer"));
                            correct_ans_result = Array.getJSONObject(0).getString("correct_answer");

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

    private void Delete(final String question, final String table_name) {

        Log.d(TAG, "Delete: Accessed");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + response);
                            String Value = jsonObject.getString("value");
                            if (Value.equals("0")) {
                                Toast.makeText(admin_show_question.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                                Intent  intent = new Intent(admin_show_question.this, admin_request.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(admin_show_question.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException ex) {
                            ex.printStackTrace();
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
                params.put("question", question);
                params.put("table", table_name);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);





    }
}

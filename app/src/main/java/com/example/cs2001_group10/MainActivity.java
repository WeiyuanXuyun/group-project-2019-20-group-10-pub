package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> saved_Topics = new ArrayList<>(); // put topics into this
    ArrayList<String> Questions = new ArrayList<>();
    public static ArrayList<String> Maths_List = new ArrayList<>();
    public static int score;
    public static ArrayList<String> Java_List = new ArrayList<>();
    public static ArrayList<String> Python_List = new ArrayList<>();
    public static String Array_Name;

    private static final String TAG = "Main Activity" ;
    private EditText email_box, password;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);

        score = 0;

        Array_Name = "maths_questions";
        Request(Api.URL_MATHS_REQUEST, Array_Name);

        Array_Name = "java_questions";
        Request(Api.URL_JAVA_REQUEST, Array_Name);

        Array_Name = "python_questions";
        Request(Api.URL_PYTHON_REQUEST, Array_Name);


        email_box = findViewById(R.id.ed_address);
        password = findViewById(R.id.ed_ps);

        Button button_register = findViewById(R.id.btn_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Register Button Worked");
                Intent intent = new Intent(MainActivity.this, new_User.class);
                startActivity(intent);
            }
        });

        Button button_login = findViewById(R.id.btn_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Login Button Worked");
                Check();
                //Intent  intent = new Intent(MainActivity.this, home_screen.class); //< - This goes to the students section
                //startActivity(intent);
            }
        });

    }

    private void Check() {
        email = this.email_box.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        if (email.matches("")) {
            Toast.makeText(this, "Email has not been entered", Toast.LENGTH_SHORT).show();
        } else if (password.matches("")) {
            Toast.makeText(this, "Password has not been entered", Toast.LENGTH_SHORT).show();
        } else {
            Login(email, password);
        }
    }

    private void Login(final String email, final String password) {
        Log.d(TAG, "Login: Accessed");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_LOGIN_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jsonObject);
                            String Value = jsonObject.getString("value");

                            switch (Value) {
                                case "0":
                                    Get_Topics(email, Api.URL_GET_TOPICS);
                                    Toast.makeText(MainActivity.this, "User Login Success", Toast.LENGTH_SHORT).show();
                                    Intent  intent = new Intent(MainActivity.this, home_screen.class); //< - This goes to the students section
                                    startActivity(intent);
                                    break;
                                case "1":
                                    Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    break;
                                case "2":
                                    Toast.makeText(MainActivity.this, "Email Address does not exist", Toast.LENGTH_SHORT).show();
                                    break;
                                case "3":
                                    Toast.makeText(MainActivity.this, "Admin Login Success", Toast.LENGTH_SHORT).show();
                                    intent = new Intent(MainActivity.this, admin_request.class);  //   < - This goes to the admin section
                                    startActivity(intent);
                                    break;
                                default:
                                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    break;

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Login Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Server Response Failed: " + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Request(String URL, final String Array_Name) { // gets table, sets it to maths_questions
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

                            if (Array_Name == "java_questions") {
                                Java_List = (ArrayList<String>) Questions.clone();
                                Questions.clear();
                            }

                            if (Array_Name == "python_questions") {
                                Python_List = (ArrayList<String>) Questions.clone();
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
                                saved_Topics.add("Maths");
                            }
                            if (java_result == 1) {
                                saved_Topics.add("Java");
                            }
                            if (python_result == 1) {
                                saved_Topics.add("Python");
                            }

                            Log.d(TAG, "onResponse: maths value: " + maths_result + " java value: " + java_result + " python value: " + python_result);
                            Log.d(TAG, "onResponse: Saved Topics" + saved_Topics);

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

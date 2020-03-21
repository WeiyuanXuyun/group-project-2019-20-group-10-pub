package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main Activity" ;
    private EditText email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);

        email = findViewById(R.id.ed_address);
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
            }
        });

    }

    private void Check() {
        final String email = this.email.getText().toString().trim();
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
                                    //intent = new Intent(MainActivity.this, request.class);     < - This goes to the admin section
                                    //startActivity(intent);
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
}

package com.example.cs2001_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class new_User extends AppCompatActivity {

    private static final String TAG = "New User Activity";
    private EditText name, email, password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__user);

        name = findViewById(R.id.ed_userName);
        email = findViewById(R.id.ed_email);
        password = findViewById(R.id.ed_pd);
        confirm_password = findViewById(R.id.ed_confirm_pd);
        Button button_register = findViewById(R.id.btn_enter);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick Working ");
                Check();
            }
        });

    }

    private void Check() {
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String confirm_password = this.confirm_password.getText().toString().trim();

        if (name.matches("")) {
            Toast.makeText(this, "Username has not been entered", Toast.LENGTH_SHORT).show();
        } else if (email.matches("")) {
            Toast.makeText(this, "Email has not been entered", Toast.LENGTH_SHORT).show();
        } else if (password.matches("")) {
            Toast.makeText(this, "Password has not been entered", Toast.LENGTH_SHORT).show();
        } else if (confirm_password.matches("")) {
            Toast.makeText(this, "Password Confirmation has not been entered", Toast.LENGTH_SHORT).show();
        } else if (!password.matches(confirm_password)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            Register(name, email, password);
        }
    }

    private void Register(final String name, final String email, final String password) {
        Log.d(TAG, "Register: Accessed");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jsonObject);
                            String Value = jsonObject.getString("value");

                            switch (Value) {
                                case "0":
                                    Toast.makeText(new_User.this, "Register Complete", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(new_User.this, MainActivity.class);
                                    startActivity(intent);
                                    break;
                                case "1":
                                    Toast.makeText(new_User.this, "Username Taken", Toast.LENGTH_SHORT).show();
                                    break;
                                case "2":
                                    Toast.makeText(new_User.this, "Email Address is already in use.", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(new_User.this, "Register Failed", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(new_User.this, "Register Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(new_User.this, "Server Response Failed: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

}

package com.example.pianoafrik.volleytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hello = (TextView) findViewById(R.id.hello);
        Map<String, String> headers =  new HashMap<>();

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBfaWQiOjEsImV4cCI6MTQ5MTAzNzk5NH0.ETbVxMDoVSksmwhprF1O7kV1fyAI1wMA959aVWFlVDQ";
        String auth = "Bearer " + token;
        headers.put("Authorization: ", auth);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mestapi-staging.herokuapp.com/api/v1/posts";

      // Request a string response from the provided URL.
        CustomStringRequest stringRequest = new CustomStringRequest(Request.Method.GET, url, headers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hello.setText(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hello.setText(error.toString());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);







    }
}


package com.example.pianoafrik.volleytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pianoafrik.volleytest.adapter.FeedsAdapter;
import com.example.pianoafrik.volleytest.model.Feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView hello;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Feed> feedList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_item);
        final FeedsAdapter adapter = new FeedsAdapter(this, 0, feedList);
        listView.setAdapter(adapter);

        //hello = (TextView) findViewById(R.id.hello);
        Map<String, String> headers =  new HashMap<>();

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBfaWQiOjEsImV4cCI6MTQ5MTA1MjMyN30.eOAhtsVLTrJm1lVCB0uYtLNFzNW5r8lcQn3JMiQVLoM";
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
                        //hello.setText(response);

                        try {
                            String body, image, mestId, time;
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){


                                JSONObject JO = jsonArray.getJSONObject(i);
                                body = JO.getString("body");
                                image = JO.getString("picture");
                                mestId = JO.getString("mester_id");
                                time = JO.getString("created_at");



                                Feed feed = new Feed(body, image, mestId, time);
                                //feedList.add(feed);
                                adapter.add(feed);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(MainActivity.this, String.valueOf(feedList.size()), Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //hello.setText(error.toString());
            }



        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }
}


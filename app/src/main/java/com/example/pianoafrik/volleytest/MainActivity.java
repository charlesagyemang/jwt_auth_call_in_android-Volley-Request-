package com.example.pianoafrik.volleytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

    private ListView listView;
    final List<Feed> feedList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.list_item);
        final FeedsAdapter adapter = new FeedsAdapter(this, 0, feedList);
        listView.setAdapter(adapter);

        Map<String, String> headers =  new HashMap<>();
        String appToken = getApplication().getResources().getString(R.string.APP_TOKEN);
        String auth = "Bearer " + appToken;
        headers.put("Authorization: ", auth);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mestapi-staging.herokuapp.com/api/v1/feeds";


        // Request a string response from the provided URL.
        CustomStringRequest stringRequest = new CustomStringRequest(Request.Method.GET, url, headers,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //hello.setText(response);

                        try {
                            String  body, image, mestId, time;
                            int id;
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){

                                JSONObject JO = jsonArray.getJSONObject(i);
                                id = JO.getInt("id");
                                body = JO.getString("body");
                                image = JO.getString("picture");
                                mestId = JO.getString("mester_id");
                                time = JO.getString("created_at");



                                Feed feed = new Feed(id, body, image, mestId, time);
//                                feedList.add(0, feed);
                                adapter.insert(feed, 0);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(MainActivity.this, String.valueOf(feedList.size()), Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //hello.setText(error.toString());
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }



        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feed feed = feedList.get(position);

                Toast.makeText(view.getContext(), String.valueOf(feed.getId()) + "\n" + feed.getTime() , Toast.LENGTH_LONG).show();
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_new:
                startActivity(new Intent(this, PostActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


package com.example.pianoafrik.volleytest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.pianoafrik.volleytest.model.Feed;
import com.example.pianoafrik.volleytest.model.FeedPost;
import com.example.pianoafrik.volleytest.model.Post;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.bitmap;

public class PostActivity extends AppCompatActivity implements IPickResult {

    EditText body;
    ImageView image;
    Bitmap bitmap;
    Button postRequest;
    File imageResult;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        body  = (EditText)findViewById(R.id.body);
        image = (ImageView) findViewById(R.id.image);
        final PickSetup setup = new PickSetup();
        postRequest = (Button)findViewById(R.id.post_request);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(setup).show(PostActivity.this);


            }
        });

        postRequest.setOnClickListener(new View.OnClickListener() {
            String picture;
            @Override
            public void onClick(View v) {






                //TODO:writer a config funtion and hide the credentials
                Map config = new HashMap();
                config.put("cloud_name", "truetestar");
                config.put("api_key",    "379534314459212");
                config.put("api_secret", "0w6J-go5CQMfSAWqFTpfbFGRrN8");
                Cloudinary cloudinary = new Cloudinary(config);




                File file = new File(uri.getPath());

                //TODO:wrte a function to get the string url after cloudinary upload
                try {
                    Map requesResult  = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                    picture = requesResult.get("url").toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String postBody = body.getText().toString().trim();
                int mesterId = 1;
                String url = "https://mestapi-staging.herokuapp.com/api/v1/feeds";
                FeedPost feed = new FeedPost( postBody,  picture, mesterId);
                //Toast.makeText(v.getContext(), post.getImage(), Toast.LENGTH_LONG).show();
                //body.setText(post.getImage());
                postFeedToApiTwo(feed, url);
                startActivity(new Intent(v.getContext(), MainActivity.class));


            }


        });

    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            this.bitmap = r.getBitmap();
            this.uri = r.getUri();
            image.setImageBitmap(r.getBitmap());


            Toast.makeText(this, r.getUri().toString(), Toast.LENGTH_LONG).show();

        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    public Bitmap getBitmapFromEncodedString (String encodedString){
        //Decode.
        byte[] decodedString = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
        //get a bitmap from from the decoded string
        Bitmap decodedByte   = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;

    }



    public void postFeedToApiTwo(FeedPost post, String URL){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("body",   post.getBody());
            jsonBody.put("picture",  post.getImage());
            jsonBody.put("mester_id", String.valueOf(post.getId()));
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(PostActivity.this, "Post successful" , Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PostActivity.this, "Post successful" + error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        //VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        Toast.makeText(PostActivity.this, "Unsupported Encoding while trying to get the bytes of %s using %s", Toast.LENGTH_LONG).show();
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    String appToken = getApplication().getResources().getString(R.string.APP_TOKEN);
                    String auth = "Bearer " + appToken;
                    params.put("Authorization: ", auth);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }




//    public  void postToApi (final Post post, String url) {

//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(PostActivity.this, "Post successful", Toast.LENGTH_LONG).show();
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(PostActivity.this, "Post unsuccessful: " + error.toString(), Toast.LENGTH_LONG).show();
//
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("mester_id", String.valueOf(post.getId()));
//                params.put("picture",   post.getImage());
//                params.put("body",      post.getBody());
//
//
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                String appToken = getApplication().getResources().getString(R.string.APP_TOKEN);
//                String auth = "Bearer " + appToken;
//                params.put("Authorization: ", auth);
//                return params;
//            }
//        };
//        queue.add(sr);


//    }


}

package com.example.pianoafrik.volleytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

import static android.R.attr.bitmap;

public class PostActivity extends AppCompatActivity implements IPickResult {

    EditText body;
    ImageView image;
    Bitmap bitmap;
    Button postRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

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
            @Override
            public void onClick(View v) {
              String encoded = getEncoded64ImageStringFromBitmap(bitmap);
              image.setImageBitmap(getBitmapFromEncodedString(encoded));

                Toast.makeText(v.getContext(), encoded, Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

              this.bitmap = r.getBitmap();

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

}

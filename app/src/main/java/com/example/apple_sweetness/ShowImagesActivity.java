package com.example.apple_sweetness;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ShowImagesActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimages);

        imageView = (ImageView)findViewById(R.id.imageView);

        Thread mThread = new Thread(){
            @Override
            public void run(){
                try{
                    //url: detected apple image URL path
                    URL url = new URL("http://192.168.2.173:777/static/squared_tree/detected_apples.jpg");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //Receive a response from the ser
                    conn.setDoInput(true);
                    //Access to the url link
                    conn.connect();

                    //is: get inputStream value
                    InputStream is = conn.getInputStream();
                    //Convert to bitmap
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try {
            mThread.join();

            //Save the image to imageView
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
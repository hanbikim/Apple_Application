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
                    URL url = new URL("http://192.168.2.173:777/static/squared_tree/detected_apples.jpg");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
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

            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
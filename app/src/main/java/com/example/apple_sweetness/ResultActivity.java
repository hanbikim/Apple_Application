package com.example.apple_sweetness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultActivity extends AppCompatActivity {

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_sweetness);

        ImageView imageView = (ImageView)findViewById(R.id.appleImg2);

        //scroll
        TextView apple_result = (TextView)findViewById(R.id.apple_result);
        apple_result.setMovementMethod(new ScrollingMovementMethod());


        //Home button
        Button home = findViewById(R.id.home_bu);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent AppleSweetnessIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(AppleSweetnessIntent);
                }
        });



        //wait

        // get_server()
        //sugar levels - text
        getSugarLevels(apple_result);
        //apple image
        getAppleImage(imageView);



    }




    private void getSugarLevels(TextView textView){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.2.173:777/txt").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ResultActivity.this, "server down", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String sugarLevels = response.body().string();

                String sugarLevel[] = sugarLevels.split(",");
                StringBuffer sugarText = new StringBuffer();

                for(int i = 0; i<sugarLevel.length; i++){
                    sugarText.append(i+1);
                    sugarText.append(". "+sugarLevel[i]+"\n");
                }
                textView.setText(sugarText);
            }
        });
    }


    private void getAppleImage(ImageView imageView){
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



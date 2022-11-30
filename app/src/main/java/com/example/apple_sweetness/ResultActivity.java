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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;

public class ResultActivity extends AppCompatActivity {
    String selectedImagePath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_sweetness);

        ImageView imageView = (ImageView)findViewById(R.id.appleImg2);

        //send image
        connect_server();


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


        // get from server()
        //sugar levels - text
        getSugarLevels(apple_result);
        //apple image
        getAppleImage(imageView);



    }




    public void connect_server() {
        // creating ipv4 address
        String ipv4Address = "192.168.2.173";
        String portNumber = "777";

        String postUrl= "http://"+ipv4Address+":"+portNumber+"/";

        // Get ready to upload image into the server
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Read BitMap by file path
        Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "appletree.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();


        // creating a client
        OkHttpClient client = new OkHttpClient();

        // building a request
        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBodyImage)
                .build();
        System.out.println("2works fine");

        // making call asynchronously
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

        try
        {
            System.out.println("Should I wait here?");
            Thread.sleep(20000);
            System.out.println("waited for 20 seconds");
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
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

                String sugarLevel[] = sugarLevels.split(", ");
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



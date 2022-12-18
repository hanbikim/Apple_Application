package com.example.apple_sweetness;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Server extends AppCompatActivity {


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        textView = findViewById(R.id.text);

        //okHttpClient: create a OkHttpClient to make a request
        OkHttpClient okHttpClient = new OkHttpClient();

        //Building a request with the local server URL
        Request request = new Request.Builder().url("http://192.168.2.173:777/txt").build();

        // Making okHttpClient call asynchronously
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            // If server is unreachable
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Server.this, "server down", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            // Got response from the server
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                //sugarLevels: the texts from the responded server
               String sugarLevels = response.body().string();

                //split the sentence by ,
                String sugarLevel[] = sugarLevels.split(",");
                StringBuffer sugarText = new StringBuffer();

                for(int i = 0; i<sugarLevel.length; i++){
                    sugarText.append(i+1);
                    sugarText.append(". "+sugarLevel[i]+"\n");
                }

                //Show apple sugar levels
                textView.setText(sugarText);
            }
        });
    }
}
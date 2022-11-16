package com.example.apple_sweetness;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }, 1500);

    }
}
<<<<<<< HEAD

=======
>>>>>>> 704c75232594750cc542526fb611c8c54c403ecb

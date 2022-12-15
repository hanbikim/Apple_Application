package com.example.apple_sweetness;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Camera
        Button CameraBtn = findViewById(R.id.Camera);
        CameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CameraIntent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(CameraIntent);
            }
        });

        //Album, Gallery
        Button AlbumBtn = findViewById(R.id.Album);
        AlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AlbumIntent = new Intent(MainActivity.this, AlbumActivity.class);
                startActivity(AlbumIntent);
            }
        });
    }
<<<<<<< HEAD

    private void ImagefromGallery() {
        Intent AlbumIntent = new Intent();
        AlbumIntent.setType("image/*");
        AlbumIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(AlbumIntent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getData();
                    Glide.with(getApplicationContext()).load(uri).into(imageView);
                }catch (Exception e){

                }
            }else if(resultCode ==RESULT_CANCELED){ //cancel code!!!

            }
        }
    }
}
=======
}
>>>>>>> refs/remotes/origin/main

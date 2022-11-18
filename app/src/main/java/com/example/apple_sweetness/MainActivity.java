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

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 0;
    ImageView imageView;

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
                setContentView(R.layout.activity_select);
                imageView = findViewById(R.id.appleImg);

                ImagefromGallery();
                textViewClick();
            }

        });
    }

    //Album
    private void ImagefromGallery() {
        Intent AlbumIntent = new Intent();
        AlbumIntent.setType("image/*");
        AlbumIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(AlbumIntent, REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
            }else if(resultCode ==RESULT_CANCELED){ //cancel code

            }
        }
    }

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }

    public String getImageFilePath(Uri uri) {

        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];

        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            cursor.close();
            return imagePath;
        }
        return null;
    }

    private void textViewClick(){
        //back
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagefromGallery();

            }
        });

        //start
        ImageView start = findViewById(R.id.go);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StartIntent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(StartIntent);
            }
        });
    }
}
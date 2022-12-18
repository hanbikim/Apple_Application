package com.example.apple_sweetness;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class AlbumActivity extends AppCompatActivity {

    //REQUEST_CODE: request code for picture
    static final int REQUEST_CODE = 0;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select);
        imageView = findViewById(R.id.appleImg);

        ImagefromAlbum();

    }


    //Send request code to Album
    private void ImagefromAlbum() {
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
                    //Use Glide library to put image in imageView
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .fitCenter()
                            .into(imageView);

                    //Selecting a picture from the album
                    BackClick();

                    //Show the result of the HML model
                    StartClick(uri);

                }catch (Exception e){
                }
            }else if(resultCode ==RESULT_CANCELED){ //if it is canceled

            }
        }
    }

    //Get the file name of image from Uri
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

    //Get Image file path from Uri
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


    //Selecting a picture from the album again
    private void BackClick(){
        //back(id: back) is a imageView in  activity_select xml file
        ImageView back = findViewById(R.id.back);

        //Call ImagefromAlbum() when clicking back imageView
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagefromAlbum();

            }
        });
    }


    //Show the result of the HML model
    private void StartClick(Uri uri){
        //start(id: go) is a imageView in activity_select xml file
        ImageView start = findViewById(R.id.go);

        //Perform ResultActivity.class when clicking start imageView
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ResultActivity.class);
                String path = getImageFilePath(uri);
                startIntent.putExtra("path", path);
                startActivity(startIntent);
            }
        });
    }
}




package com.example.apple_sweetness;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AlbumActivity extends AppCompatActivity {

    // One Button
    Button button;

    // One Preview Image
    ImageView imageView;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toast.makeText(AlbumActivity.this, "!!!g", Toast.LENGTH_LONG).show();

        // register the UI widgets with their appropriate IDs
        button = findViewById(R.id.BSelectImage);
        imageView = findViewById(R.id.iv_photo);

        ImagefromGallery();
    }


    private void ImagefromGallery() {
//        Intent AlbumIntent = new Intent(Intent.ACTION_PICK);
//        AlbumIntent.setType("image/*");
//        startActivityForResult(AlbumIntent, SELECT_PICTURE);


        Intent AlbumIntent = new Intent();
        AlbumIntent.setType("image/*");
        AlbumIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(AlbumIntent, REQUEST_CODE);
    }
    // this function is triggered when user
    // selects the image from the imageChooser
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK) {
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                imageView.setImageBitmap(selectedImage);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(AlbumActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        }else {
//            Toast.makeText(AlbumActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
//        }

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



package com.example.apple_sweetness;

import static java.lang.System.out;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    final private static String TAG = "Top Gun";
    ImageView appleImg;

    final static int TAKE_PICTURE = 1;

    //mCurrentPhotoPath: saving the path
    String mCurrentPhotoPath;
    //REQUEST_TAKE_PHOTO: request for taking a picture
    final static int REQUEST_TAKE_PHOTO = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        appleImg = findViewById(R.id.appleImg);

        //set permission after checking the permission status
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "finished for giving authorization");
            }
            else {
                Log.d(TAG, "request for authorization");
                ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        //Execute camera intent for taking a picture from the camera
        dispatchTakePictureIntent();

        out.close();

        //back(id: back) is a imageView in  activity_select xml file
        ImageView back = findViewById(R.id.back);

        //call dispatchTakePictureIntent() when clicking back imageView
        //taking a picture from the camera again
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

        //start(id: go) is a imageView in activity_select xml file
        ImageView start = findViewById(R.id.go);

        //perform ResultActivity.class when clicking start imageView
        //show the result of the HML model
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StartIntent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(StartIntent);
            }
        });

    }

    // Request camera permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }


    // Display image taken from camera in the imageView taken from camera
    //Use bitmap for displaying high resolution image in imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {//getBitmap() function doesn't work on API 29 version
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) { appleImg.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {//getBitmap() function works on under API 29 version
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) { appleImg.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    // Save image as file
    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File cacheDir = getCacheDir();

        //Save the image in cache storage
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                cacheDir
        );

        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    // Execute camera intent
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        if(getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            File photoFile = null;

            try {
                photoFile = createImageFile();

                // create empty file
                photoFile.createNewFile();

                FileOutputStream out = new FileOutputStream(photoFile);
            }
            catch (IOException ex) {

            }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.FileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
   }


}


package com.example.sudheesha.lab5;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class PhotoActivity extends ActionBarActivity {
    public static final int TAKE_PHOTO = 1;
    ImageView image;
    static Bitmap pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Button capture = (Button) findViewById(R.id.takePhoto);
        image = (ImageView) findViewById(R.id.view_photo);

        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK && data != null) {
            pic = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(pic);
            Log.d("CameraDemo", "Pic saved");
        } else if (resultCode == RESULT_OK && data != null) {
            try {
                pic = MediaStore.Images.Media.getBitmap(getApplicationContext().
                        getContentResolver(), data.getData());
                image.setImageBitmap(pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void redirectToHome(View v) {

        Intent redirect = new Intent(PhotoActivity.this, MainActivity.class);
        startActivity(redirect);
    }

}

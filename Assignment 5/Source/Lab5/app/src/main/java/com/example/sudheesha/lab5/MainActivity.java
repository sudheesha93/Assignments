package com.example.sudheesha.lab5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {
    Button button_map;
    Button button_photo;
    Button b2;
    int TAKE_PHOTO_CODE = 0;
    int SELECT_FILE=1;
    ImageView userImage ;
    static Bitmap photo;

    EditText name1;
    EditText name2;
    EditText pswrd;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b2=(Button)findViewById(R.id.add);

        userImage = (ImageView) findViewById(R.id.profilePic);
        button_map = (Button) findViewById(R.id.add);
        button_photo = (Button) findViewById(R.id.cam);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    public void SignUpButton(){
        name1=(EditText)findViewById(R.id.fname);
        name2=(EditText)findViewById(R.id.lname);
        pswrd=(EditText)findViewById(R.id.pass);
        signup=(Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name1!=null && name2!=null && pswrd!=null)
                {
                    Toast.makeText(MainActivity.this,"SignUp Successful",Toast.LENGTH_LONG).show();
                    // Intent i=new Intent(MainActivity.this,MainActivity.class);
                    //MainActivity.this.startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Fields cant be null. They are mandatory",Toast.LENGTH_LONG).show();
                    // Intent i=new Intent(MainActivity.this,MainActivity.class);
                    // MainActivity.this.startActivity(i);
                }
                {
                }
            }
        });
    }

    public void onClickOfPhotoButton(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(photo);
            Log.d("CameraDemo", "Pic saved");
        }
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            Bitmap bm=null;
            try {
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch(Exception E){};
            Bitmap bms = Bitmap.createScaledBitmap(bm, 250, 250, false);
            photo=bms;
            userImage.setImageBitmap(photo);

            Log.d("CameraDemo", "Gallery pic saved");
        }
    }
    public void uploadClick(View v){
        Intent intent = new Intent(
                Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

}
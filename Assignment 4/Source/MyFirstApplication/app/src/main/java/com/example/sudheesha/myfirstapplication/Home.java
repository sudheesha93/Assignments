package com.example.sudheesha.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private static Button tts;
    private static Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tts=(Button)findViewById(R.id.button1);
        map=(Button)findViewById(R.id.button2);

        tts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Home.this, UserActivity.class);
                Home.this.startActivity(intent);
            }
        }
        );

        map.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Home.this, MapsActivity.class);
                Home.this.startActivity(intent);
            }
        }
        );



    }
}

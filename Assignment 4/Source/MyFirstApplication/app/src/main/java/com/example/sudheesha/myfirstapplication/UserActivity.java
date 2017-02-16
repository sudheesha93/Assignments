package com.example.sudheesha.myfirstapplication;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class UserActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener{
TextToSpeech tos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        tos=new TextToSpeech(UserActivity.this, UserActivity.this);
        final EditText text=(EditText)findViewById(R.id.editText);
        final Button b=(Button)findViewById(R.id.button);
        final TextView textView=(TextView)findViewById(R.id.editText);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(!tos.isSpeaking()){
                    HashMap<String,String> hm=new HashMap<String, String>();
                    hm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"text");
                    tos.speak(textView.getText().toString(), TextToSpeech.QUEUE_ADD, hm);
                    b.setVisibility(Button.GONE);
                }
                else{
                    tos.stop();
                }
            }


        });




    }
    @Override
    public void onInit(int i){
        tos.setOnUtteranceCompletedListener(this);

    }

    @Override
    public void onUtteranceCompleted(String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UserActivity.this,"Utterance completed",Toast.LENGTH_LONG).show();
                Button button=(Button)findViewById(R.id.button);
                button.setVisibility(Button.VISIBLE);
            }
        });

    }

    protected void onDestroy()
    {
        if(tos!=null)
        {
            tos.stop();
            tos.shutdown();
            tos=null;
        }
        super.onDestroy();
    }
}

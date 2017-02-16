package com.example.sudheesha.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class Registration extends AppCompatActivity {


    private static EditText name;
    private static EditText pswrd;
    private static EditText mail;
    private static EditText age;
    private static Button regBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Register();
    }

    public void Register()
    {
        name=(EditText)findViewById(R.id.editname);
        pswrd=(EditText)findViewById(R.id.editpass);
        mail=(EditText)findViewById(R.id.editemail);
        age=(EditText)findViewById(R.id.editage);
        regBtn=(Button)findViewById(R.id.regbutton);

        regBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().length()!=0 && pswrd.getText().toString().length() !=0 &&
                                mail.getText().toString().length()!= 0 && age.getText().toString().length()!= 0) {
                            Toast.makeText(Registration.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Registration.this,Login.class);
                            Registration.this.startActivity(i);
                        } else {
                            Toast.makeText(Registration.this, "Sorry..You have missed entering some details", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
                }


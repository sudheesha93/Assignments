package com.example.sudheesha.myfirstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    private static EditText username;
    private static EditText password;
    private static TextView attempts;
    private static Button login_btn;
    private static TextView reg_link;
    int attempt_counter=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();


        reg_link =(TextView)findViewById(R.id.reg);
        reg_link.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Intent intent=new Intent(Login.this, Registration.class);
                                            Login.this.startActivity(intent);
                                        }
                                    }
        );
    }

    public  void LoginButton() {
        username = (EditText) findViewById(R.id.editname);
        password = (EditText) findViewById(R.id.editpass);
        attempts = (TextView) findViewById(R.id.attemptid);
        login_btn = (Button) findViewById(R.id.button2);

        attempts.setText(Integer.toString(attempt_counter));
        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (username.getText().toString().equals("admin") &&
                                password.getText().toString().equals("abc123")) {
                            Toast.makeText(Login.this, "Username and Password are correct",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this,Home.class);
                            Login.this.startActivity(i);
                        } else {
                            Toast.makeText(Login.this, "Username and Password are incorrect",
                                    Toast.LENGTH_SHORT).show();
                            attempt_counter--;
                            attempts.setText(Integer.toString(attempt_counter));
                            if (attempt_counter == 0) {
                                login_btn.setEnabled(false);
                            }

                        }
                    }

                }
        );
    }

}

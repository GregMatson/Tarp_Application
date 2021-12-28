package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private int version = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText)findViewById(R.id.usn);
        pass = (EditText)findViewById(R.id.psw);
        ToggleButton type = (ToggleButton)findViewById(R.id.loginType);
        Button login = (Button) findViewById(R.id.loginBtn);
        Button signUp = (Button)findViewById(R.id.signBtn);

        type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    version = 1;
                } else {
                    // The toggle is disabled
                    version = 0;
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIN(user.getText().toString(),pass.getText().toString(), version);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUP();
            }
        });
    }

    private void logIN(String user, String pass, int type){
        if((type==0) && (user.equals("Karl")) && (pass.equals("Wheez"))) {   //Change to getting Student info from server
            Intent stulog = new Intent(MainActivity.this, StudentLoginActivity.class);
            startActivity(stulog);
        }
        else if((type==1) && (user.equals("Karl")) && (pass.equals("Wheez"))) {   //Change to getting Teacher info from server
            Intent teachlog = new Intent(MainActivity.this, TeacherLoginActivity.class);
            startActivity(teachlog);
        }
    }

    private void signUP(){
        Intent signup = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signup);
    }
}
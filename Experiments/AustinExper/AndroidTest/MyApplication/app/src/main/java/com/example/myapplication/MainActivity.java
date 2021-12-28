package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button wheez;
    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wheez = (Button)findViewById(R.id.btn);
        user = (EditText)findViewById(R.id.usn);
        pass = (EditText)findViewById(R.id.psw);

        wheez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(user.getText().toString(),pass.getText().toString());
            }
        });

    }

    private void click(String user, String pass){
       if((user.equals("Karl")) && (pass.equals("Wheez"))) {
           Intent intent = new Intent(MainActivity.this, SecondActivity.class);
           startActivity(intent);
       }
    }
}
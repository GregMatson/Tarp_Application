package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = findViewById(R.id.button);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0){
        if(arg0.getId() == R.id.button){
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        }
    }
}
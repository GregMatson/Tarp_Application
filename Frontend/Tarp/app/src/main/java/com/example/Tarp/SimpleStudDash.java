package com.example.Tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SimpleStudDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_stud_dash);

        /**
         * button to go to classes
         */
        Button sclasses = (Button)findViewById(R.id.sclasses);
        /**
         * button to go to map
         */
        Button smap = (Button)findViewById(R.id.smap);
        /**
         * button to go to profile
         */
        Button sprofile = (Button)findViewById(R.id.sprofile);
        /**
         * button to logout
         */
        Button slogout = (Button)findViewById(R.id.slogout);

        Button chat = (Button)findViewById(R.id.sChat);
        /**
         * logs out the user
         */
        slogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(SimpleStudDash.this, MainActivity.class);
                startActivity(logout);
            }
        });


        /**
         * goes to students classes
         */
        sclasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sclasses = new Intent(SimpleStudDash.this, StudentClasses.class);
                startActivity(sclasses);
            }
        });


        /**
         * goes to student profile
         */
        sprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sprofile = new Intent(SimpleStudDash.this, StudentProfile.class);
                startActivity(sprofile);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sChat = new Intent(SimpleStudDash.this, SimpleChatRoom.class);
                startActivity(sChat);
            }
        });

    }
}
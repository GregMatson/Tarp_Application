package com.example.Tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SimpleTeacherDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_teacher_dash);

        /**
         * Button to go to classes
         */
        Button tclasses = (Button)findViewById(R.id.tclasses);
        /**
         * Button to go to map
         */
        Button tmap = (Button)findViewById(R.id.tmap);
        /**
         * button to go to profile
         */
        Button tprofile = (Button)findViewById(R.id.tprofile);
        /**
         * Button to logout
         */
        Button tlogout = (Button)findViewById(R.id.tlogout);

        Button chat = (Button)findViewById(R.id.tChat);
        /**
         * logs out the user
         */
        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(SimpleTeacherDash.this, MainActivity.class);
                startActivity(logout);
            }
        });


        /**
         * goes to user classes
         */
        tclasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tclasses = new Intent(SimpleTeacherDash.this, TeacherClasses.class);
                startActivity(tclasses);
            }
        });


        /**
         * goes to user profile
         */
        tprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tprofile = new Intent(SimpleTeacherDash.this, TeacherProfile.class);
                startActivity(tprofile);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tChat = new Intent(SimpleTeacherDash.this, SimpleChatRoom.class);
                startActivity(tChat);
            }
        });
    }
}
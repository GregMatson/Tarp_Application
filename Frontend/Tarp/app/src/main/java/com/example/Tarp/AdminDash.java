package com.example.Tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        Button classes = (Button)findViewById(R.id.aClasses);
        Button users = (Button)findViewById(R.id.aUsers);
        Button chat = (Button)findViewById(R.id.aChats);
        Button logout = (Button)findViewById(R.id.alogout);


        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aClass = new Intent(AdminDash.this, AdminRemoveClass.class);
                startActivity(aClass);
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aUsers = new Intent(AdminDash.this, AdminRemoveUser.class);
                startActivity(aUsers);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aLogout = new Intent(AdminDash.this, MainActivity.class);
                startActivity(aLogout);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aChat = new Intent(AdminDash.this, SimpleChatRoom.class);
                startActivity(aChat);
            }
        });
    }
}
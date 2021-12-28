package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;
import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Tarp.model.Student;
import com.example.Tarp.model.Teacher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherProfile extends AppCompatActivity {

    /**
     * Text view to display users' name
     */
    private TextView name;
    /**
     * Text view to display username
     */
    private TextView username;
    /**
     * text view to display users' email
     */
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        name = (TextView)findViewById(R.id.tProfileName);
        username = (TextView)findViewById(R.id.tProfileUser);
        email = (TextView)findViewById(R.id.tProfileEmail);
        /**
         * Button to switch view to edit
         */
        Button edit = (Button)findViewById(R.id.tProfileEdit);
        /**
         * Button to switch view to dasboard
         */
        Button dashboard = (Button)findViewById(R.id.tProfileDash);

        /**
         * Switches the view to teacher Dashboard
         */
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tEditProfile = new Intent(TeacherProfile.this, TeacherEditProfile.class);
                startActivity(tEditProfile);
            }
        });

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID",0);

        /**
         * gets the user info from the database
         */
        GetTeacherApi().getTeacherByID(userID).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                name.setText(response.body().getFirstName() + (" ") +  response.body().getLastName());
                username.setText(response.body().getUserName());
                email.setText((response.body().getEmail()));
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {

            }
        });
    }
}
package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetClassesApi;
import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Tarp.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProfile extends AppCompatActivity {

    /**
     * text view to display users' name
     */
    private TextView name;
    /**
     * text view to display the username
     */
    private TextView username;
    /**
     * text view to display users' email
     */
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        name = (TextView)findViewById(R.id.sProfileName);
        username = (TextView)findViewById(R.id.sProfileUser);
        email = (TextView)findViewById(R.id.sProfileEmail);
        /**
         * Button to switch to edit view
         */
        Button edit = (Button)findViewById(R.id.sProfileEdit);
        /**
         * Button to switch to Dashboard
         */
        Button dashboard = (Button)findViewById(R.id.sProfileDash);

        /**
         * switches view to Dashboard
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
                Intent editProfile = new Intent(StudentProfile.this, StudentEditProfile.class);
                startActivity(editProfile);
            }
        });

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID",0);

        /**
         * gets user information
         */
        GetStudentApi().getStudentByID(userID).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                name.setText(response.body().getFirstName() + (" ") + response.body().getLastName());
                username.setText(response.body().getUserName());
                email.setText((response.body().getEmail()));
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });
    }
}
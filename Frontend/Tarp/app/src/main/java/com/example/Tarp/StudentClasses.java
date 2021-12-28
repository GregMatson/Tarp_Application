package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetClassesApi;
import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Tarp.model.Classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentClasses extends AppCompatActivity {

    /**
     * text view that displays current students classes
     */
    private TextView stuClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classes);

        /**
         * Button to switch to add class view
         */
        Button sAddClass = (Button)findViewById(R.id.addClassButton);
        /**
         * button to switch to drop class view
         */
        Button sDropClass = (Button)findViewById(R.id.dropClassButton);
        /**
         * button to switch to grade view
         */
        Button sGrades = (Button)findViewById(R.id.stuGrades);
        /**
         * button to switch to student dash view
         */
        Button sToDash = (Button)findViewById(R.id.dashboardButton);
        stuClassList = (TextView)findViewById(R.id.stuClassList);

        stuClassList.setMovementMethod(new ScrollingMovementMethod());

        /**
         * switches view to add class
         */
        sAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addClass = new Intent(StudentClasses.this,StudentAddClass.class);
                startActivity(addClass);
                displayClasses();
            }
        });

        /**
         * switches view to drop class
         */
        sDropClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dropClass = new Intent(StudentClasses.this, StudentRemoveClass.class);
                startActivity(dropClass);
                displayClasses();
            }
        });

        /**
         * switches view to grades
         */
        sGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent grades = new Intent(StudentClasses.this, StudentGrades.class);
                startActivity(grades);
                displayClasses();
            }
        });


        /**
         * switches view to student dash
         */
        sToDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(StudentClasses.this, SimpleStudDash.class);
                startActivity(dash);
            }
        });

        displayClasses();


    }

    /**
     * displays the list of classes the student is enrolled in
     */
    private void displayClasses() {
        stuClassList.setText("");
        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID",0);
        GetStudentApi().getStudentsSchedule(userID).enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if(response.body() != null) {
                    for (int i = response.body().size() - 1; i >= 0; i--) {
                        stuClassList.append(response.body().get(i).stuPrintable());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                    stuClassList.setText("failed");
            }
        });
    }
}
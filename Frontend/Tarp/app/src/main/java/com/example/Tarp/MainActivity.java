package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetAdminApi;
import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;
import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.Tarp.model.Admin;
import com.example.Tarp.model.Student;
import com.example.Tarp.model.Teacher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * text box for the username
     */
    private EditText username;
    /**
     * text box for the password
     */
    private EditText password;
    /**
     * popup text for failed to login
     */
    private TextView failLogin;
    /**
     * switch to chooses login typ
     */
    private TextView typeInfo;
    /**
     * stores the login type as int
     */
    private int version = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.login_activity_userName);
        password = (EditText)findViewById(R.id.login_activity_passWord);
        failLogin = (TextView)findViewById(R.id.failedLogin);
        CheckBox student = (CheckBox)findViewById(R.id.studentCheck);
        CheckBox teacher = (CheckBox)findViewById(R.id.teacherCheck);
        CheckBox admin = (CheckBox)findViewById(R.id.adminCheck);
        Button login = (Button) findViewById(R.id.loginBtn);
        Button signUp = (Button)findViewById(R.id.signBtn);


        /**
         * Sets the login type
         */
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher.setChecked(false);
                admin.setChecked(false);
                version = 0;
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setChecked(false);
                admin.setChecked(false);
                version = 1;
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setChecked(false);
                teacher.setChecked(false);
                version = 2;
            }
        });


        /**
         * attempt to login the user
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIN(username.getText().toString(),password.getText().toString(), version);
            }
        });

        /**
         * goes to the signup screen
         */
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUP();
            }
        });
    }

    /**
     * Attempts to login the user
     * @param username the users username
     * @param pass the users password
     * @param type the login type
     */
    private void logIN(String username, String pass, int type) {

    SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
    SharedPreferences.Editor editor = currentUser.edit();
    editor.clear();     //clears the current values
    editor.apply();


    if(type==0){

        Student studentCheck = new Student(username, pass);
        /**
         * calls the student API to check if student user exists
         */
        GetStudentApi().loginValidation(studentCheck).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(!(response.body() == null)){
                    failLogin.setText("");
                    editor.putInt("currentUserID", response.body().getId());
                    editor.putString("currentUserName", username);
                    editor.apply();
                    Intent stulog = new Intent(MainActivity.this, SimpleStudDash.class);
                    startActivity(stulog);
                }
                else{
                    password.setError("Username or Password incorrect");
                    failLogin.setBackgroundResource(R.color.white);
                    failLogin.setText(R.string.failLogin);
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });

    }

    else if(type == 1) {

        Teacher teacherCheck = new Teacher(username, pass);
        /**
         * calls the teacher API to check if teacher user exists
         */
        GetTeacherApi().loginValidation(teacherCheck).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if(!(response.body() == null)){   
                    failLogin.setText("");
                    editor.putInt("currentUserID", response.body().getId());
                    editor.putString("currentUserName", username);
                    editor.apply();
                    Intent tealog = new Intent(MainActivity.this, SimpleTeacherDash.class);
                    startActivity(tealog);
                }
                else{
                    password.setError("Username or Password incorrect");
                    failLogin.setBackgroundResource(R.color.white);
                    failLogin.setText(R.string.failLogin);
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {

            }
        });
    }
    else if(type==2){

        GetAdminApi().loginValidation(username, pass).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if(!(response.body() == null)){
                    failLogin.setText("");
                    editor.putInt("currentUserID", response.body().getId());
                    editor.putString("currentUserName", username);
                    editor.apply();
                    Intent adminlog = new Intent(MainActivity.this, AdminDash.class);
                    startActivity(adminlog);
                }
                else {
                    password.setError("Username or Password incorrect");
                    failLogin.setBackgroundResource(R.color.white);
                    failLogin.setText(R.string.failLogin);
                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                failLogin.setBackgroundResource(R.color.white);
                failLogin.setText(R.string.responseFailure);
            }
        });
    }
}

    /**
     * sends user to the signup screen
     */
    private void signUP(){
        Intent signup = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signup);
    }

}


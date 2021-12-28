package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;
import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Tarp.model.Student;
import com.example.Tarp.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentEditProfile extends AppCompatActivity {

    EditText first, last, user, email, passOld, passNew, passCon;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile);

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID", 0);
        String currUser = currentUser.getString("currentUserName", "");

        message = (TextView) findViewById(R.id.sEditMessage);

        first = (EditText) findViewById(R.id.sEditName);
        last = (EditText) findViewById(R.id.sEditLastName);
        user = (EditText) findViewById(R.id.sEditUser);
        email = (EditText) findViewById(R.id.sEditEmail);
        passOld = (EditText) findViewById(R.id.sEditPass);
        passNew = (EditText) findViewById(R.id.sEditNewPass);
        passCon = (EditText) findViewById(R.id.sEditNewPassCon);

        Button confirm = (Button) findViewById(R.id.sConfirmEdit);
        Button cancel = (Button) findViewById(R.id.sCancelEdit);

        setValues(userID);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equals(currUser)) {
                    GetStudentApi().getStudentByID(userID).enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            if (response.body() != null) {
                                if (response.body().getPassword().equals(passOld.getText().toString())) {

                                    if(passNew.getText().toString().matches("")){
                                        editUser(first.getText().toString(), last.getText().toString(), user.getText().toString(),
                                                email.getText().toString(), "----");
                                    }
                                    else if (passCon.getText().toString().equals(passNew.getText().toString())) {
                                        editUser(first.getText().toString(), last.getText().toString(), user.getText().toString(),
                                                email.getText().toString(), passNew.getText().toString());
                                    }

                                    else {
                                        passCon.setError("New Password Does Not Match");
                                    }
                                }
                                else {
                                    passOld.setError("Incorrect Password");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {

                        }
                    });
                }

                else {
                    GetStudentApi().findStuUserName(user.getText().toString()).enqueue(new Callback<List<Student>>() {
                        @Override
                        public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                            if (response.body().isEmpty()) {
                                GetStudentApi().getStudentByID(userID).enqueue(new Callback<Student>() {
                                    @Override
                                    public void onResponse(Call<Student> call, Response<Student> response) {
                                        if (response.body() != null) {
                                            if (response.body().getPassword().equals(passOld.getText().toString())) {

                                                if(passNew.getText().toString().matches("")){
                                                    editUser(first.getText().toString(), last.getText().toString(), user.getText().toString(),
                                                            email.getText().toString(), "----");
                                                }
                                                else if (passCon.getText().toString().equals(passNew.getText().toString())) {
                                                    editUser(first.getText().toString(), last.getText().toString(), user.getText().toString(),
                                                            email.getText().toString(), passNew.getText().toString());
                                                }

                                                else {
                                                    passCon.setError("New Password Does Not Match");
                                                }
                                            } else {
                                                passOld.setError("Incorrect Password");
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Student> call, Throwable t) {

                                    }
                                });
                            }
                            else {
                                user.setError("Username Already Taken");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Student>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }


    private void setValues(int userID) {
        GetStudentApi().getStudentByID(userID).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                first.setText(response.body().getFirstName());
                last.setText(response.body().getLastName());
                user.setText(response.body().getUserName());
                email.setText((response.body().getEmail()));
                passOld.setText((response.body().getPassword()));
                passNew.setText("");
                passCon.setText("");
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });
    }

    private void editUser(String first, String last, String user, String email, String passNew) {
        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = currentUser.edit();
        int userID = currentUser.getInt("currentUserID",0);


        GetStudentApi().editStudentProfile(userID, first, last, user, email, passNew).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                message.setBackgroundResource(R.color.white);
                message.setText(R.string.sEditConfirm);
                editor.putString("currentUserName", user);
                editor.apply();
                setValues(userID);

            }
        });

    }
}
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

public class TeacherEditProfile extends AppCompatActivity {

    EditText first, last, user, email, passOld, passNew, passCon;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit_profile);

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID",0);
        String currUser = currentUser.getString("currentUserName", "");

        message = (TextView)findViewById(R.id.tEditMessage);

        first = (EditText)findViewById(R.id.tEditName);
        last = (EditText)findViewById(R.id.tEditLastName);
        user = (EditText)findViewById(R.id.tEditUser);
        email = (EditText)findViewById(R.id.tEditEmail);
        passOld = (EditText)findViewById(R.id.tEditPass);
        passNew = (EditText)findViewById(R.id.tEditNewPass);
        passCon = (EditText)findViewById(R.id.tEditNewPassCon);

        Button confirm = (Button)findViewById(R.id.tConfirmEdit);
        Button cancel = (Button)findViewById(R.id.tCancelEdit);

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

                if (user.getText().toString().equals(currUser)){
                    GetTeacherApi().getTeacherByID(userID).enqueue(new Callback<Teacher>() {
                        @Override
                        public void onResponse(Call<Teacher> call, Response<Teacher> response) {
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
                        public void onFailure(Call<Teacher> call, Throwable t) {

                        }
                    });
                }

                else {
                    GetTeacherApi().findTeaUserName(user.getText().toString()).enqueue(new Callback<List<Teacher>>() {
                        @Override
                        public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                            if (response.body().isEmpty()) {
                                GetTeacherApi().getTeacherByID(userID).enqueue(new Callback<Teacher>() {
                                    @Override
                                    public void onResponse(Call<Teacher> call, Response<Teacher> response) {
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
                                    public void onFailure(Call<Teacher> call, Throwable t) {

                                    }
                                });
                            }
                            else {
                                user.setError("Username Already Taken");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Teacher>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void setValues(int userID) {
        GetTeacherApi().getTeacherByID(userID).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                first.setText(response.body().getFirstName());
                last.setText(response.body().getLastName());
                user.setText(response.body().getUserName());
                email.setText((response.body().getEmail()));
                passOld.setText((response.body().getPassword()));
                passNew.setText("");
                passCon.setText("");
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {

            }
        });
    }

    private void editUser(String first, String last, String user, String email, String passNew) {
        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = currentUser.edit();
        int userID = currentUser.getInt("currentUserID",0);

        GetTeacherApi().editTeacherProfile(userID, first, last, user, email, passNew).enqueue(new Callback<String>() {
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
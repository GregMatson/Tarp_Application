package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetClassesApi;
import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;
import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Tarp.model.Classes;
import com.example.Tarp.model.Student;
import com.example.Tarp.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRemoveUser extends AppCompatActivity {

    EditText user;
    TextView userList, message;
    private int type = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_user);

        user = (EditText)findViewById(R.id.deleteUserName);
        userList = (TextView)findViewById(R.id.DeleteUserList);
        message = (TextView)findViewById(R.id.aDeleteUserMessage);
        Button delete = (Button)findViewById(R.id.aDeleteUser);
        Button cancel = (Button)findViewById(R.id.cancelDeleteUser);
        CheckBox student = (CheckBox)findViewById(R.id.aStudentCheck);
        CheckBox teacher = (CheckBox)findViewById(R.id.aTeacherCheck);

        userList.setMovementMethod(new ScrollingMovementMethod());
        displayAllUsers();

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher.setChecked(false);
                type = 0;
                displayStudents();
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setChecked(false);
                type = 1;
                displayTeachers();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 5){
                    user.setError("Please choose a user type above");
                    message.setBackgroundResource(R.color.white);
                    message.setText("Please choose a user type above");
                }
                else if(type == 0){
                    GetStudentApi().checkStudentExist(user.getText().toString()).enqueue(new Callback<List<Student>>() {
                        @Override
                        public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                            if(!(response.body().isEmpty())) {
                                GetStudentApi().deleteStudent(user.getText().toString()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        message.setBackgroundResource(R.color.white);
                                        message.setText(R.string.deleteUserConfirm);
                                        user.setText("");
                                        student.setChecked(false);
                                        teacher.setChecked(false);
                                        displayAllUsers();
                                    }
                                });
                            }
                            else {
                                message.setBackgroundResource(R.color.white);
                                message.setText(R.string.UserNotFound);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Student>> call, Throwable t) {

                        }
                    });

                }
                else if(type == 1){
                    GetTeacherApi().checkTeacherExist(user.getText().toString()).enqueue(new Callback<List<Teacher>>() {
                        @Override
                        public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                            if(!(response.body().isEmpty())){
                                GetTeacherApi().deleteTeacher(user.getText().toString()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        message.setBackgroundResource(R.color.white);
                                        message.setText(R.string.deleteUserConfirm);
                                        user.setText("");
                                        displayAllUsers();
                                    }
                                });
                            }
                            else {
                                message.setBackgroundResource(R.color.white);
                                message.setText(R.string.UserNotFound);
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

    private void displayStudents() {
        userList.setText("");
            GetStudentApi().GetAllStudents().enqueue(new Callback<List<Student>>() {
                @Override
                public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                    if (response.body() != null) {
                        for (int i = response.body().size() - 1; i >= 0; i--) {
                            userList.append(response.body().get(i).printable());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Student>> call, Throwable t) {

                }
            });

        }

    private void displayTeachers() {
        userList.setText("");
        GetTeacherApi().getAllTeachers().enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                if (response.body() != null) {
                    for (int i = response.body().size() - 1; i >= 0; i--) {
                        userList.append(response.body().get(i).printable());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {

            }
        });


    }

    private void displayAllUsers() {
        userList.setText("");
            GetStudentApi().GetAllStudents().enqueue(new Callback<List<Student>>() {
                @Override
                public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                    if (response.body() != null) {
                        displayStudents();
                    }
                }

                @Override
                public void onFailure(Call<List<Student>> call, Throwable t) {

                }
            });
            GetTeacherApi().getAllTeachers().enqueue(new Callback<List<Teacher>>() {
                @Override
                public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                    if (response.body() != null) {
                        displayTeachers();
                    }
                }

                @Override
                public void onFailure(Call<List<Teacher>> call, Throwable t) {

                }
            });
    }
}
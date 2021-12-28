package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Tarp.model.Classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRemoveClass extends AppCompatActivity {

    private TextView classList, message;
    private EditText className, classSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_remove_class);

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID",0);

        classList = (TextView)findViewById(R.id.stuClassListRemove);
        message = (TextView)findViewById(R.id.removeError);
        className = (EditText)findViewById(R.id.removeClassName);
        classSection = (EditText)findViewById(R.id.removeClassSection);
        Button remove = (Button)findViewById(R.id.studentRemoveClass);
        Button cancel = (Button)findViewById(R.id.cancelRemoveClass);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(StudentRemoveClass.this, StudentClasses.class);
                startActivity(cancel);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(className.getText().toString().equals("")) && !(classSection.getText().toString().equals(""))) {

                    //Finds if entered class is in classlist, returns class ID if found, 0 if not found
                    GetStudentApi().findStudentClass(userID, className.getText().toString(), classSection.getText().toString()).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.body().intValue() != 0){

                                //removes the class form the students class list, returns class ID
                                GetStudentApi().unassignStudentFromCourse(userID, response.body().intValue()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        message.setBackgroundResource(R.color.white);
                                        message.setText(R.string.classRemoveSuccess);
                                        //updates class list on view
                                        classList.setText("");
                                        className.setText("");
                                        classSection.setText("");
                                        displayClasses(userID);
                                    }
                                });
                            }
                            else {
                                message.setBackgroundResource(R.color.white);
                                message.setText(R.string.removeNotFound);
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                }
                else {
                    message.setBackgroundResource(R.color.white);
                    message.setText(R.string.removeFillAll);
                }
            }
        });

        displayClasses(userID);
    }

    /**
     * displays the list of classes the student is enrolled in
     */
    private void displayClasses(int userID) {
        GetStudentApi().getStudentsSchedule(userID).enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if(response.body() != null) {
                    for (int i = response.body().size() - 1; i >= 0; i--) {
                        classList.append(response.body().get(i).stuPrintable());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                classList.setText(R.string.responseFailure);
            }
        });
    }
}

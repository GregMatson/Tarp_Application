package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetClassesApi;
import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;
import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Tarp.model.Classes;
import com.example.Tarp.model.Teacher;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAClass extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aclass);

        /**
         * pop to display error message
         */
        TextView error = (TextView)findViewById(R.id.createError);
        /**
         * Text box to enter class Name
         */
        EditText className= (EditText) findViewById(R.id.className);
        /**
         * Text box to enter class Time
         */
        EditText classTime = (EditText) findViewById(R.id.classTime);
        /**
         * Text box to enter Class Section
         */
        EditText classSection = (EditText) findViewById(R.id.classSection);
        /**
         * Text box to enter Class Description
         */
        EditText classDescription = (EditText) findViewById(R.id.classDescription);
        /**
         * button to cancel class creation
         */
        Button cancel = (Button)findViewById(R.id.cancelCreate);
        /**
         * Button to create a new class
         */
        Button createClass = (Button)findViewById(R.id.createClass);

        /**
         * cancel create-a-class and switches view to teacher classes
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(CreateAClass.this, TeacherClasses.class);
                startActivity(cancel);
            }
        });

        /**
         * makes the class Description text box scrollable
         */
        classDescription.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });

        /**
         * creates a new class based on info set in text boxes
         */
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(className.getText().toString().equals("")) && !(classTime.getText().toString().equals("")) &&
                        !(classSection.getText().toString().equals("")) && !(classDescription.getText().toString().equals(""))){
                    GetClassesApi().checkClassExist(className.getText().toString(),classSection.getText().toString()).enqueue(new Callback<List<Classes>>() {
                        @Override
                        public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                            if(response.body().isEmpty()){
                                createNewClass(className.getText().toString().toUpperCase(), classTime.getText().toString().toUpperCase(),
                                        classSection.getText().toString().toUpperCase(), classDescription.getText().toString());

                                className.setText("");
                                classTime.setText("");
                                classSection.setText("");
                                classDescription.setText("");

                                error.setBackgroundResource(R.color.white);
                                error.setText(R.string.createdClass);
                        }
                            else{
                                error.setBackgroundResource(R.color.white);
                                error.setText(R.string.classExist);
                            }


                    }

                        @Override
                        public void onFailure(Call<List<Classes>> call, Throwable t) {

                        }
                        });


                }

                else{
                    error.setBackgroundResource(R.color.white);
                    error.setText(R.string.createUnfilled);
                }
            }
        });

    }

    /**
     * creates a new class in the database
     * @param className name of class
     * @param classTime time of class
     * @param classSection class section
     * @param classDescript class description
     */
    private void createNewClass(String className, String classTime, String classSection, String classDescript) {

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        Classes newClass = new Classes(className, classTime, classSection,  classDescript,
                currentUser.getString("currentUserName",""), currentUser.getInt("currentUserID",0));

        GetClassesApi().addClassToList(newClass).enqueue(new Callback<Classes>() {
            @Override
            public void onResponse(Call<Classes> call, Response<Classes> response) {
                if(response.isSuccessful()){
                    relateCourseToTeacher(className, classSection);
                }
            }

            @Override
            public void onFailure(Call<Classes> call, Throwable t) {

            }
        });


    }

    /**
     * relates the new class to the teacher that created it in the database
     * @param className name of the class
     * @param classSection section of the class
     */
    private void relateCourseToTeacher(String className, String classSection) {

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID",0);

        GetClassesApi().getClassByNameAndSection(className, classSection).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body().intValue() != 0) {
                    GetTeacherApi().assignCourseToTeacher(userID, response.body().intValue()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
}
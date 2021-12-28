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
import android.widget.EditText;
import android.widget.TextView;

import com.example.Tarp.model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAddClass extends AppCompatActivity {

    /**
     * text view to display class list
     */
    private TextView sClassList;
    /**
     * popup to display error messages
     */
    private TextView message;
    /**
     * text box to enter class name
     */
    private EditText sClassName;
    /**
     * text box to enter class section
     */
    private EditText sClassSection;

    private List<String> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_class);

        /**
         * button to enter and search for class name
         */
        Button setName = (Button) findViewById(R.id.enterClassName);
        /**
         * button to add the class using section and name
         */
        Button setSection = (Button) findViewById(R.id.studentAddClass);
        /**
         * button to cancel adding a class
         */
        Button cancelAdd = (Button) findViewById(R.id.cancelAddClass);
        sClassName = (EditText) findViewById(R.id.addClassName);
        sClassSection = (EditText) findViewById(R.id.enterClassSection);
        sClassList = (TextView) findViewById(R.id.addClassList);
        message = (TextView) findViewById(R.id.confirmAdd);

        classes = getAllClasses();

        /**
         * sets classlist textbox to be scrollable
         */
        sClassList.setMovementMethod(new ScrollingMovementMethod());

        /**
         * enters the section of the class and adds the class to students classlist
         */
        setSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sClassSection.getText().toString().equals("") && !sClassName.getText().toString().equals("")) {
                    GetClassesApi().getClassByNameAndSection(sClassName.getText().toString(), sClassSection.getText().toString()).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.body().intValue() != 0) {
                                enrollStudentToClass(response.body().intValue());
                                sClassName.setText("");
                                sClassSection.setText("");
                                message.setBackgroundResource(R.color.white);
                                message.setText(R.string.addedClass);
                                displayAllClasses();
                            }
                            else{
                                sClassSection.setError("Section not Found");
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            message.setBackgroundResource(R.color.white);
                            sClassName.setError("Class not found");
                            message.setText(R.string.failAddClass);
                        }
                    });
                }
                else {
                    message.setBackgroundResource(R.color.white);
                    message.setText(R.string.addIncomplete);
                }
            }
        });

        /**
         * sends user back to class page
         */
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(StudentAddClass.this, StudentClasses.class);
                startActivity(cancel);
            }
        });


        /**
         * enters the name and displays a filtered classlist
         */
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sClassList.setText("");
                Classes search = new Classes(sClassName.getText().toString());
                GetClassesApi().reducedList(search).enqueue(new Callback<List<Classes>>() {
                    @Override
                    public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                        if (response.body() != null) {
                            sClassList.scrollTo(0, 0);
                            displayClasses(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Classes>> call, Throwable t) {

                    }
                });
            }
        });

        displayAllClasses();
    }

    private List<String> getAllClasses() {
        List<String> listNames = new ArrayList<String>();
        GetClassesApi().getClassList().enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful())
                {
                    for (int i = 0; i < response.body().size(); i++)
                    {
                        listNames.add(response.body().get(i).getClassName());
                    }
                }
                else
                    {
                        try{
                            JSONObject error = new JSONObject(response.errorBody().toString());
                            String e = error.getString("message");
                            sClassList.setText(e);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }


            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {

            }
        });
        return listNames;
    }

    public boolean isClass(List<String> classList, String aClass){
        return classList.contains(aClass);
    }

    /**
     * Assigns the class to the student for use in the database
     * @param classID ID of the class to assign to student
     */
    private void enrollStudentToClass(int classID) {

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int userID = currentUser.getInt("currentUserID",0);

        GetStudentApi().assignCourseToStudent(userID, classID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                message.setBackgroundResource(R.color.white);
//                message.setText(R.string.responseFailure);
            }
        });
    }

    /**
     * displays the list of classes
     * @param response the list of classes to be displayed
     */
    private void displayClasses(Response<List<Classes>> response) {
        if (!(response.body().isEmpty())){
            for (int i = response.body().size() - 1; i >= 0; i--) {
                sClassList.append(response.body().get(i).classPrintable());
            }
    }


}

    /**
     * displays all classes in classlist
     */
    private void displayAllClasses() {
        GetClassesApi().getClassList().enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.body() != null){
                    sClassList.setText("");
                    displayClasses(response);
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {

            }
        });
    }
    }
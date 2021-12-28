package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetStudentApi;
import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.Tarp.model.Student;
import com.example.Tarp.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends Activity {

    /**
     * users first name
     */
    private EditText firstName;
    /**
     * users last name
     */
    private EditText lastName;
    /**
     * users username
     */
    private EditText username;
    /**
     * users email
     */
    private EditText email;
    /**
     * users password
     */
    public EditText password;
    /**
     * confirm users password
     */
    private EditText conPassWord;
    /**
     * popup text for not matching passwords
     */
    private TextView failSign;
    /**
     * popup text for user already exists
     */
    private TextView failSignUser;
    /**
     * user signup type
     */
    private int version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = (EditText) findViewById(R.id.signup_activity_firstName);
        lastName = (EditText) findViewById(R.id.signup_activity_lastName);
        email = (EditText) findViewById(R.id.signup_activity_eMail);
        username = (EditText) findViewById(R.id.signup_activity_userName);
        password = (EditText) findViewById(R.id.signup_activity_passWord);
        conPassWord = (EditText) findViewById(R.id.signup_activity_conPassWord);
        failSign = (TextView) findViewById(R.id.failedSignupPass);
        failSignUser = (TextView) findViewById(R.id.failedSignupUser);
        ToggleButton type = (ToggleButton) findViewById(R.id.loginType2);
        Button signup = (Button) findViewById(R.id.signup);
        Button cancel = (Button) findViewById(R.id.cancel);

        /**
         * Sets whether sign-up type is student or teacher
         */
        type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    version = 1; //teacher
                } else {
                    // The toggle is disabled
                    version = 0; //student
                }
            }
        });

        /**
         * cancels signup and goes back to login
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * attempts to signup the user
         */
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPass(password.getText().toString(), conPassWord.getText().toString())) {
                    if (version == 0) {
                        GetStudentApi().findStuUserName(username.getText().toString()).enqueue(new Callback<List<Student>>() {
                            @Override
                            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                                if (response.body().isEmpty()) {
                                    failSign.setText("");
                                    sendStuData(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString());
                                    finish();
                                }
                                else {
                                    failSignUser.setBackgroundResource(R.color.white);
                                    username.setError("Username is taken");
                                    failSignUser.setText(R.string.failUser);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Student>> call, Throwable t) {

                            }
                        });
                    }
                     else if (version == 1) {
                        GetTeacherApi().findTeaUserName(username.getText().toString()).enqueue(new Callback<List<Teacher>>() {
                            @Override
                            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                                if(response.body().isEmpty()){
                                    failSign.setText("");
                                    sendTeachData(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString());
                                    finish();
                                }
                                else {
                                    failSignUser.setBackgroundResource(R.color.white);
                                    username.setError("Username is taken");
                                    failSignUser.setText(R.string.failUser);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Teacher>> call, Throwable t) {

                            }
                        });
                    }
                else {
                    failSign.setBackgroundResource(R.color.white);
                    conPassWord.setError("Passwords do not match");
                    failSign.setText(R.string.failSign);
                }

                }
            }
        });
    }

    /**
     * Checks whether the password matches confirm password
     * @param password users password
     * @param conPassWord users confirm password
     * @return whether the password matches the confirm password
     */
    public Boolean checkPass(String password, String conPassWord){
        return password.equals(conPassWord);
    }

    /**
     * creates new student and sends that student to the database
     * @param firstName students first name
     * @param lastName students last name
     * @param email students email
     * @param username students username
     * @param password students password
     */
    public void sendStuData(String firstName, String lastName,  String email, String username, String password){

        Student student = new Student(firstName, lastName,  email, username, password);
        GetStudentApi().addStudent(student).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                //signUp();
                System.out.println(call.request().body().toString());
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });

    }

    /**
     * creates new teacher and sends that teacher to the database
     * @param firstName teachers first name
     * @param lastName teachers last name
     * @param email teachers email
     * @param username teachers username
     * @param password teachers password
     */
    private void sendTeachData(String firstName, String lastName, String email, String username, String password){

        //TODO Debug
        Teacher teacher = new Teacher(firstName, lastName,  email, username, password);
        GetTeacherApi().addTeacher(teacher).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                //signUp();
                System.out.println(call.request().body().toString());
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {

            }
        });
    }

}
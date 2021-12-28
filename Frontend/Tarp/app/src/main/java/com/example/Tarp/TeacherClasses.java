package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetClassesApi;
import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;

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

public class TeacherClasses extends AppCompatActivity {

    /**
     * text view to display teacher class list
     */
    private TextView teachClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classes);

        /**
         * Button to switch view to create-a-class
         */
        Button createClass = (Button)findViewById(R.id.createClassButton);
        /**
         * Button to switch view to dashboard
         */
        Button tBackClass = (Button)findViewById(R.id.tBackClass);
        /**
         * Button to switch view to remove class
         */
        Button tremoveClass = (Button)findViewById(R.id.tRemoveClass);

        teachClass= (TextView)findViewById(R.id.teachClassList);

        teachClass.setMovementMethod(new ScrollingMovementMethod());

        /**
         * switches view to create-a-class
         */
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAClass = new Intent(TeacherClasses.this, CreateAClass.class);
                startActivity(createAClass);
            }
        });

        /**
         * switches view to teacher dashboard
         */
        tBackClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(TeacherClasses.this, SimpleTeacherDash.class);
                startActivity(dash);
            }
        });

        /**
         * Switches view to teacher remove class
         */
        tremoveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tremove = new Intent(TeacherClasses.this, TeacherRemoveClass.class);
                startActivity(tremove);
            }
        });

        showClassList();

    }

    /**
     * displays the classes the teacher has created
     */
    private void showClassList() {

        SharedPreferences currentUser = getSharedPreferences("currentUSER", this.MODE_PRIVATE);
        int teachID = currentUser.getInt("currentUserID",0);

        GetTeacherApi().getClassesBeingTaught(teachID).enqueue(new Callback<List<Classes>>() {

            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.body() != null){
                    for (int i = response.body().size()-1; i >= 0; i--) {
                        teachClass.append(response.body().get(i).teachPrintable());
                    }
            }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {

            }
        });

    }
}
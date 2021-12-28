package com.example.Tarp;

import static com.example.Tarp.Api.ApiClientFactory.GetClassesApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Tarp.model.Classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRemoveClass extends AppCompatActivity {

   private EditText className, classSection;
   private TextView classList, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_class);

        message = (TextView)findViewById(R.id.aDeletemessage);
        classList = (TextView)findViewById(R.id.DeleteClassList);
        className = (EditText)findViewById(R.id.DeleteClassName);
        classSection = (EditText)findViewById(R.id.aEnterClassSection);
        Button delete = (Button)findViewById(R.id.aDeleteClass);
        Button cancel = (Button)findViewById(R.id.cancelDeleteClass);
        Button search = (Button)findViewById(R.id.aSearchClass);


        displayAllClasses();

        classList.setMovementMethod(new ScrollingMovementMethod());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(className.getText().toString().equals("")){
                    className.setError("Please enter a class");
                    displayAllClasses();
                }
               else {
                    classList.setText("");
                    Classes search = new Classes(className.getText().toString());
                    GetClassesApi().reducedList(search).enqueue(new Callback<List<Classes>>() {
                        @Override
                        public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                            if (response.body() != null) {
                                classList.scrollTo(0, 0);
                                displayClasses(response);
                            } else {
                                classList.setText(R.string.classNotFound);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Classes>> call, Throwable t) {

                        }
                    });
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!classSection.getText().toString().equals("") && !className.getText().toString().equals("")) {
                    GetClassesApi().checkClassExist(className.getText().toString(), classSection.getText().toString()).enqueue(new Callback<List<Classes>>() {
                        @Override
                        public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                            if(!(response.body().isEmpty())){
                                GetClassesApi().deleteFromList(className.getText().toString(), classSection.getText().toString()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        message.setBackgroundResource(R.color.white);
                                        message.setText(R.string.classDeleted);

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        message.setBackgroundResource(R.color.white);
                                        message.setText(R.string.classDeleted);
                                        className.setText("");
                                        classSection.setText("");
                                        displayAllClasses();

                                    }
                                });
                            }
                            else{
                                message.setBackgroundResource(R.color.white);
                                message.setText(R.string.classNotFound);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Classes>> call, Throwable t) {

                        }
                    });

                }
                else{
                    className.setError("Please fill all fields");
                    classSection.setError("Please fill all fields");
                }

            }
        });

    }

    /**
     * displays the list of classes
     * @param response the list of classes to be displayed
     */
    private void displayClasses(Response<List<Classes>> response) {
        if (response.body() != null) {
            for (int i = response.body().size() - 1; i >= 0; i--) {
                classList.append(response.body().get(i).adminPrintable());
            }
        }
    }

    private void displayAllClasses() {
        GetClassesApi().getClassList().enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.body() != null){
                    classList.setText("");
                    displayClasses(response);
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {

            }
        });
    }
}
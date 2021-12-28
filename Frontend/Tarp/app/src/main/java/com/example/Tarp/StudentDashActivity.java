package com.example.Tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.Tarp.databinding.ActivityMainBinding;


public class StudentDashActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash);

        Button dashclass = (Button)findViewById(R.id.dashclass);
        Button dashmap = (Button)findViewById(R.id.dashmap);
        Button dashprof = (Button)findViewById(R.id.dashprof);
        Button dashlogout = (Button)findViewById(R.id.dashlogout);

        dashlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashlogout = new Intent(StudentDashActivity.this, MainActivity.class);
                startActivity(dashlogout);
            }
        });


        dashclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashclass = new Intent(StudentDashActivity.this, StudentClasses.class);
                startActivity(dashclass);
            }
        });


        dashprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashprof = new Intent(StudentDashActivity.this, StudentProfile.class);
                startActivity(dashprof);
            }
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_dashboard, R.id.navigation_profile, R.id.navigation_classes)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
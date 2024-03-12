package com.example.fitbit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class Workout_Plans extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plans);
    }
    public void Beginner(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Beginner_Level.class);
        startActivity(intent);
    }
    public void Intermediate(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Intermediate_Level.class);
        startActivity(intent);
    }
    public void Advanced(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Advanced_Level.class);
        startActivity(intent);
    }

}
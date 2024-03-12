package com.example.fitbit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class Meal_Plan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);
    }

    public void Gain(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Weight_Gain.class);
        startActivity(intent);
    }
    public void Loss(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Weight_Loss.class);
        startActivity(intent);
    }
}
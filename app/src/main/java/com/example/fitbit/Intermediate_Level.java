package com.example.fitbit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Intermediate_Level extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate_level);
    }
    public void Mon(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Monday_Intermediate.class);
        startActivity(intent);
        finish();
    }
    public void Tue(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Tuseday_Intermediate.class);
        startActivity(intent);
        finish();

    }
    public void Wed(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Wednesday_Intermediate.class);
        startActivity(intent);
        finish();

    }
    public void Thu(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Thursday_Intermediate.class);
        startActivity(intent);
        finish();

    }
    public void Fri(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Friday_Intermediate.class);
        startActivity(intent);
        finish();

    }
    public void Sat(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Saturday_Intermediate.class);
        startActivity(intent);
        finish();

    }
    public void Sun(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Sunday_Intermediate.class);
        startActivity(intent);
        finish();

    }
}
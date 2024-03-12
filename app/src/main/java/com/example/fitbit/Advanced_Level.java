package com.example.fitbit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Advanced_Level extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);
    }
    public void Mon1(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Monday_Advanced.class);
        startActivity(intent);
        finish();

    }
    public void Tue1(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Tuesday_Advanced.class);
        startActivity(intent);
        finish();

    }
    public void Wed1(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Wednesday_Advanced.class);
        startActivity(intent);
        finish();

    }
    public void Thu1(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Thursday_Intermediate.class);
        startActivity(intent);
        finish();

    }
    public void Fri1(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Friday_Advanced.class);
        startActivity(intent);
        finish();

    }
    public void Sat1(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Saturday_Advanced.class);
        startActivity(intent);
        finish();

    }
    public void Sun1(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Sunday_Advanced.class);
        startActivity(intent);
        finish();

    }
}
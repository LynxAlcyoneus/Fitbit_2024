package com.example.fitbit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



public class Home_Screen extends AppCompatActivity {

    private ImageButton logoutButton;
    FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        logoutButton = findViewById(R.id.imageButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log the user out
                mAuth.signOut();

                // Redirect the user to the login screen or another appropriate screen
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (logoutButton == null) {
            Log.e("HomeScreenActivity", "Logout button is null in onResume()");
            return;
        }
        logoutButton.setEnabled(true);
    }


    public void GuestBMI(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void WorkoutPlans(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Workout_Plans.class);
        startActivity(intent);
    }
    public void MealPlan(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Meal_Plan.class);
        startActivity(intent);
    }
    public void HealthTips(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Health_Tips.class);
        startActivity(intent);
    }
    public void CalorieCounter(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Calorie_Counter.class);
        startActivity(intent);
    }

    public void HB(View view) {
        Intent intent = new Intent(this, com.example.fitbit.Camera.class);
        startActivity(intent);

    }

}
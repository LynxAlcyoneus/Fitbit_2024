package com.example.fitbit;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;



public class Calorie_Counter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);

        Spinner spinnerGenders=findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Genders, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerGenders.setAdapter(adapter);
    }


    public void Calories(View v){
        float bmr = 0;
        int calories = 0;

       // Spinner gender1 = findViewById(R.id.spinner);
        EditText age1 = findViewById(R.id.ENTage);
        EditText weight1 = findViewById(R.id.ENTweit);
        EditText height1 = findViewById(R.id.ENTheight);
        EditText here = findViewById(R.id.TExt);
        Button button = findViewById(R.id.Calcu);

        Spinner spinner = findViewById(R.id.spinner);
        String selectedValue = spinner.getSelectedItem().toString();
        //String gender = gender1.toString();
        float  age = Float.parseFloat(age1.getText().toString());
        float  weight = Float.parseFloat(weight1.getText().toString());
        float height = Float.parseFloat(height1.getText().toString());


        // Calculate basal metabolic rate (BMR)
        if (selectedValue.equals("Male") ){
            bmr = (float) (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age));

        } else if (selectedValue.equals("Female")) {
            bmr = (float) (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age));
        } else {
            System.out.println("Invalid gender input. Please enter 'male' or 'female'.");

        }


        // Calculate calories based on BMR and activity level
        // This example assumes a sedentary activity level
        calories = (int)(bmr * 1.2);

        here.setText(+calories + " cal");
    }



    // public void CaloriesResult(View view){
    //     Intent intent = new Intent(this,CaloriesResult.class);
    //     startActivity(intent);
    // }


}
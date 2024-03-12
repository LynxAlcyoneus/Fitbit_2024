package com.example.fitbit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Calculate(View v) {

        EditText et1 = findViewById(R.id.editTextNumberDecimal);
        EditText et2 = findViewById(R.id.editTextNumberDecimal2);
        EditText et3 = findViewById(R.id.editTextNumberDecimal3);
        Button button = findViewById(R.id.button);
        String a = et1.getText().toString();
        String b = et2.getText().toString();
        Float n1 = Float.parseFloat(a);
        Float n2 = Float.parseFloat(b);

        Float result = n1 * 10000 / (n2 * n2);
        Double result2 = Math.round(result * 100) / 100.0;
        et3.setText(" " + result2);

    }
}
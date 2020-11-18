package com.example.ex092;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * @author		Shahar Yani
 * @version     1.0
 * @since		06/11/2020
 * The MainActivity.class gets the inputs and send them to showTheObjects.class
 */
public class MainActivity extends AppCompatActivity {

    EditText distance, firstObject;
    Switch type;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstObject = findViewById(R.id.firstObject);
        distance = findViewById(R.id.distance);
        type = findViewById(R.id.type);
    }

    /**
     * When the button is clicked Show the series checks if the inputs are good to showTheObjects.class.
     * @param view the view
     */
    public void showTheSeries(View view) {
        if (firstObject.getText().toString().isEmpty() || distance.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        else if (firstObject.getText().toString().equals("-") || firstObject.getText().toString().equals(".") || firstObject.getText().toString().equals("-.")  || distance.getText().toString().equals(".") || distance.getText().toString().equals("-") || distance.getText().toString().equals("-.")){
            Toast.makeText(this, " - or . cannot without numbers", Toast.LENGTH_SHORT).show();
        }
        else{
            flag = type.isChecked();
            Intent si = new Intent(this, showTheObjects.class);
            si.putExtra("firstObject", firstObject.getText().toString());
            si.putExtra("distance", distance.getText().toString());
            si.putExtra("type",flag);
            startActivity(si);
        }
    }
}
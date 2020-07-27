package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.tensorflow_inference.pm10;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Double[] input = {1.0,
            6.0,
            2.0,
            11.0,
            2.0,
            2.0,
            1.0,
            3.0,
            4.0,
            3.0,
            10.0,
            6.0,
            10.0,
            6.0,
            4.0,
            3.0,
            3.0,
            3.0,
            3.0,
            4.0,
            4.0,
            4.0,
            5.0,
            4.0};


    private pm10 inference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Double[] output;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            output = inference.run_inference(this,input);


        }catch (Exception e){
            Log.d("error in main ....................",e.getMessage());
        }

    }
}

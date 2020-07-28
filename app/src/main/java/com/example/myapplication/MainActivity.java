package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.tensorflow_inference.pm10;
import com.example.myapplication.tensorflow_inference.pm25;


public class MainActivity extends AppCompatActivity {
    float[] input = {1.0f,
            6.0f,
            2.0f,
            11.0f,
            2.0f,
            2.0f,
            1.0f,
            3.0f,
            4.0f,
            3.0f,
            10.0f,
            6.0f,
            10.0f,
            6.0f,
            4.0f,
            3.0f,
            3.0f,
            3.0f,
            3.0f,
            4.0f,
            4.0f,
            4.0f,
            5.0f,
            4.0f};


    private pm10 inference10 =  new pm10();
    private pm25 inference25 =  new pm25();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        float[][] output = new float[][]{{0,0,0,0,0,0}};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // todo set input variable to required format , note please set as float.

        //todo call pm10.run_inference and pm25.run_inference for using the model.
        try {
             output = inference10.run_inference(this,input);
        }catch (Exception e){
            Log.d("error in main ....................",e.getMessage());
        }

    }
}

package com.example.myapplication.tensorflow_inference;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public   class pm10 {
    protected double mean = 29.494944444444446;
    protected double std = 21.626718993905616;
    protected Interpreter tflite;
    protected  int inp_size = 24;
    protected int op_size = 6;

    protected String path =  "model_pm10.tflite";





    private MappedByteBuffer loadModelFile(Activity activity, String MODEL_FILE) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    public float[][] run_inference(Activity activity , float[] input ){
        float[][] output = new float[][]{{0,0,0,0,0,0}};
        float[] processed_input;


        try {
            tflite = new Interpreter(loadModelFile(activity,path));

            processed_input = preprocess(input);
            tflite.run(processed_input, output);
            output = decode(output);
            Log.d("Output.........", String.valueOf(output[0][0]));
            close();




        } catch (Exception e){
            Log.d("error",e.getMessage());
        }
    return output;
    }
    protected float[] preprocess(float[] input){
        float[] output = new float[inp_size];
        int i = 0;

        for(i=0;i<inp_size;i++){
            output[i] = (float) ((input[i] - mean)/std);
        }
        return output;



    }
    protected float[][] decode(float[][] input){
        float[][] output = new float[][]{{0,0,0,0,0,0}};
        int i = 0;

        for(i=0;i<op_size;i++){
            output[0][i] = (float) ((input[0][i] * std) +mean);
        }
        return output;



    }
    public void close() {
        tflite.close();
        tflite = null;
        // TODO: Close the interpreter

    }



}

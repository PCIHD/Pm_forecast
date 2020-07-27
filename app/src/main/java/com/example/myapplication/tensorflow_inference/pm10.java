package com.example.myapplication.tensorflow_inference;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public abstract  class pm10 {
    protected double mean = 25.144111111111112;
    protected double std = 17.155964586013322;
    protected Interpreter tflite;

    protected String path =  "/assets/model_pm25/tflite";





    private MappedByteBuffer loadModelFile(Activity activity, String MODEL_FILE) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    public Double[] run_inference(Activity activity , Double[] input ){
        Double[] output = null;


        try {
            tflite = new Interpreter(loadModelFile(activity,path));

            input = preprocess(input).toArray(new Double[0]);
            tflite.run(input, output);
            output = decode(output).toArray(new Double[0]);
            Log.d("Output.........",output.toString());




        } catch (Exception e){
            Log.d("error","message");
        }
    return output;
    }
    protected List<Double> preprocess(Double[] input){
        List<Double> output = null;
        int i = 0;
        int length = input.length;
        for(i=0;i<length;i++){
            output.add((input[i] - mean)/std);
        }
        return output;



    }
    protected List<Double> decode(Double[] input){
        List<Double> output = null;
        int i = 0;
        int length = input.length;
        for(i=0;i<length;i++){
            output.add((input[i] * std) *mean);
        }
        return output;



    }
    public void close() {
        tflite.close();
        tflite = null;
        // TODO: Close the interpreter

    }



}

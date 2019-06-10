package com.example.jake_the_dog;

import android.util.Log;

public class run_thread extends Thread {

    run_thread() {

    }

    public void run() {

        Log.d("Jake", "Running run thread...");

        try{
            for(int i=0; i < 10; i++) {
                Thread.sleep(1000);
                Log.d("Jake", "Seconds in run thread are: " +
                        i);
            }

        }catch (InterruptedException exc) {
            Log.d("Jake", "Run Thread interuppted.");
        }
        Log.d("Jake", "Run Thread terminated.");
    }
}

/*

A separate time thread that will keep track of the minutes going by.

 */

package com.example.jake_the_dog;

import android.util.Log;

public class time_thread implements Runnable {


    private jake passed_dog;

    time_thread(jake p_dog) {

        passed_dog = p_dog;
    }

    public void run() {


        Log.d("Jake", "Starting Time Thread....");

        try {

            for (int sec_count=0; sec_count < 240; sec_count++) {
                Log.d("Jake", "jake is alive: " + passed_dog.ask_alive());
                if (!passed_dog.ask_alive()) {
                    passed_dog.jake_dead();
                    break;
                }

                if (sec_count % 2 == 0) {

                    passed_dog.setM_hunger(-5);
                    passed_dog.setM_thirst(-5);
                    passed_dog.setM_clean(-5);
                    passed_dog.setM_bored(-5);
                }

                if (sec_count % 10 == 0) {

                    Log.d("Jake", "Dog HUNGER IS: " + passed_dog.getM_hunger());
                    Log.d("Jake", "Dog THIRST IS: " + passed_dog.getM_thirst());
                    Log.d("Jake", "Dog CLEAN IS: " + passed_dog.getM_clean());
                    Log.d("Jake", "Dog BORED IS: " + passed_dog.getM_bored());
                }

                Thread.sleep(1000);
                Log.d("Jake", sec_count +
                        " seconds have went by.");
            }


        }
        catch (InterruptedException exc) {
            System.out.println(" interuppted.");
        }
        System.out.println(" terminating.");
    }
}

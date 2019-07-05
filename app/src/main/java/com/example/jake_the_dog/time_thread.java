/*

A separate time thread that will keep track of the minutes going by.

 */

package com.example.jake_the_dog;

import android.util.Log;
import android.widget.ImageView;

import java.util.Random;

public class time_thread implements Runnable {

    // Need to create memory allocation for the dog reference passed into this Thread.

    private jake passed_dog;

    // Constructor which takes a passed dog reference as a parameter.

    time_thread(jake p_dog) {

        passed_dog = p_dog;
    }

    // Factory-fitted run method, which begins a counting loop and checks conditions as it counts.

    // This is the main time_thread which queues and executes different methods within dog, adjusting
    // stats as time goes by. The time_thread continually checks if dog.ask_alive == true, if not
    // the loop ends and the player is kicked out of the game.

    // In the future this will be controlled with a WHILE loop.

    public void run() {

        int r_mod;
        int r_dir;

        Log.d("Jake", "Starting Time Thread....");

        try {

            for (int sec_count=0; sec_count < 240; sec_count++) {

                Log.d("Jake", "jake is alive: " + passed_dog.ask_alive());

                // Set up variables for a random modulo and random direction number.
                // These variables dictate when and in what direction Jake moves.

                int low_rand_mod = 2;
                int high_rand_mod = 25;

                int low_rand_dir = 0;
                int high_rand_dir = 5000;

                Random rand_modulo = new Random();
                Random rand_direction = new Random();

                r_mod = rand_modulo.nextInt(high_rand_mod-low_rand_mod) + low_rand_mod;
                r_dir = rand_direction.nextInt(high_rand_dir-low_rand_dir) + low_rand_dir;

                if (sec_count % r_mod == 0) {

                    if(r_dir <= 2500) {

                        passed_dog.walk_dog_left();
                    }
                    else {
                        passed_dog.walk_dog_right();
                    }
                }

                if (!passed_dog.ask_alive()) {
                    passed_dog.jake_dead();
                    break;
                }

                if (sec_count % 2 == 0) {

                    passed_dog.setM_hunger(-0.5);
                    passed_dog.setM_thirst(-0.5);
                    passed_dog.setM_clean(-0.5);
                    passed_dog.setM_bored(-0.5);


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

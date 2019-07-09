/*

A separate time thread that will keep track of the minutes going by.

 */

package com.example.jake_the_dog;

import android.util.Log;
import android.widget.ImageView;

import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class time_thread implements Runnable {

    // Need to create memory allocation for the dog reference passed into this Thread.

    private jake passed_dog;
    private boolean interacting = false;
    private MainActivity act;
    private int cont_inter = 0;

    // Constructor which takes a passed dog reference as a parameter.

    time_thread(jake p_dog, MainActivity p_act) {

        passed_dog = p_dog;
        act = p_act;
    }

    public void set_interacting(boolean y) {
        interacting = y;
    }

    public boolean get_interacting() {

        return interacting;
    }

    // Factory-fitted run method, which begins a counting loop and checks conditions as it counts.

    // This is the main time_thread which queues and executes different methods within dog, adjusting
    // stats as time goes by. The time_thread continually checks if dog.ask_alive == true, if not
    // the loop ends and the player is kicked out of the game.

    public void run() {

        int r_dir;
        int r_scratch;

        Log.d("Jake", "Starting Time Thread....");

        try {

            for (int sec_count=0; sec_count < 480; sec_count++) {

                // Set up variables for a random modulo and random direction number.
                // These variables dictate when and in what direction Jake moves.

                int low_rand_mod = 2;
                int high_rand_mod = 25;

                int low_rand_scratch = 0;
                int high_rand_scratch = 5000;

                int low_rand_dir = 0;
                int high_rand_dir = 5000;

                Random rand_modulo = new Random();
                Random rand_direction = new Random();
                Random rand_scratch = new Random();

                r_dir = rand_direction.nextInt(high_rand_dir-low_rand_dir) + low_rand_dir;
                r_scratch = rand_scratch.nextInt(high_rand_scratch-low_rand_scratch) + low_rand_scratch;

                if (!interacting) {

                    if (r_scratch <= 1500) {

                        passed_dog.dog_scratches();

                    }

                    // This IF decides whether he moves or not.

                    if (sec_count % 10 == 0) {

                        // This IF decides what movement he makes.

                        // This IF decides whether he walks left or right.

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

                // So here we are asking, "is the user interacting?" and if so, skip all animations
                // and continue to count the time down and adjust the stats.

                else {

                    if(cont_inter == 15) {

                        cont_inter = 0;
                        act.undraw_ball();
                        this.set_interacting(false);

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
                    cont_inter += 1;
                    Log.d("Jake", sec_count +
                            " seconds have went by while USER INTERACTING...");

                }
            }
        }
        catch (InterruptedException exc) {
            System.out.println(" interuppted.");
        }
        System.out.println(" terminating.");
    }
}

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
    private boolean is_peeing = false;
    private MainActivity act;
    private int cont_inter = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private int pee_count = 0;

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

    public void set_peeing(boolean y) {
        is_peeing = y;
    }

    public boolean get_is_peeing() {

        return is_peeing;
    }

    public void reset_cont_iter() {

        cont_inter = 0;
    }

    // Factory-fitted run method, which begins a counting loop and checks conditions as it counts.

    // This is the main time_thread which queues and executes different methods within dog, adjusting
    // stats as time goes by. The time_thread continually checks if dog.ask_alive == true, if not
    // the loop ends and the player is kicked out of the game.

    public void run() {

        int r_dir;
        double hunger;
        double thirst;
        float cleanliness;
        float bored;

        Log.d("Jake", "Starting Time Thread....");

        try {

            for (int mil_sec_count=0; mil_sec_count < 250000; mil_sec_count++) {

                // Set up variables for a random modulo and random direction number.
                // These variables dictate when and in what direction Jake moves.

                // This FOR loop runs for 100 ms so I want to move the clouds every time it
                // rolls around.

                hunger = passed_dog.getM_hunger();
                thirst = passed_dog.getM_thirst();

                Log.d("Jake", "HUNGER IN TIME THREAD: " + hunger);

                // TODO: IF statements to check hunger and thirst levels in order to display
                // TODO: prompts and trigger appropriate sounds.

                // TODO: Also need to check boredom and cleanliness stats and play sounds or change
                // TODO: backgrounds or something. With boredom he should bark a lot. When dirty
                // TODO: I need something to happen.

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        act.move_cloud_one();
                        act.move_cloud_two();
                    }
                });

                int low_rand_scratch = 0;
                int high_rand_scratch = 5000;
                Random rand_dir = new Random();

                r_dir = rand_dir.nextInt(high_rand_scratch-low_rand_scratch) + low_rand_scratch;

                // Counting seconds and adjusting stats.

                if (mil_sec_count % 10 == 0) {
                    seconds += 1;
                    Log.d("Jake", seconds +
                            " seconds have went by.");

                    // Hunger and thirst go down by 0.0075 every second. This allows the dog to live
                    // for a day and a half without food or water.

                    passed_dog.setM_hunger(-0.0075);
                    passed_dog.setM_thirst(-0.0075);
                    passed_dog.setM_clean(-0.5);
                    passed_dog.setM_bored(-0.5);

                }

                if (!interacting) {

                    // We always count the seconds and drop the stats in both interacting and non-interacting.

                    // If is_peeing is true then we skip all the arbitrary animations.

                    if(get_is_peeing()) {

                        // We need the pee-count counter to count off the animation time.
                        // This avoids clashing with walking and scratching animations.

                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(pee_count > 2) {
                                    set_peeing(false);
                                }
                                else {
                                    pee_count += 1;

                                }
                            }
                        });

                        Thread.sleep(100);
                    }
                    else {

                        // Milliseconds 150ms = 15s

                        if (mil_sec_count % 150 == 0 ) {

                            passed_dog.dog_scratches();

                        }

                        if (mil_sec_count == 50) {

                            Log.d("Jake", "DOG TEST PEE AFTER TWO MINUTES!");
                            set_peeing(true);
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    act.dog_pees();
                                }
                            });
                        }

                        // This IF decides whether he moves or not.
                        // Measuring in milliseconds, so every 100 ms = 10s.

                        if (mil_sec_count % 900 == 0) {

                            // 50 / 50 whether he will walk left or right.
                            // He walks every ten seconds.

                            if (r_dir <= 2500) {

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

                        Thread.sleep(100);

                    }


                }

                // So here we are asking, "is the user interacting?" and if so, skip all animations.

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

                    Thread.sleep(100);

                }
            }
        }
        catch (InterruptedException exc) {
            System.out.println(" interuppted.");
        }
        System.out.println(" terminating.");
    }
}

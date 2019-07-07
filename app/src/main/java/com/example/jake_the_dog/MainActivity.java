package com.example.jake_the_dog;
import androidx.annotation.LongDef;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    jake fresh_dog;

    Button feed_button;
    Button water_button;
    Button play_button;
    Button clean_button;
    Button stats_button;

    boolean play_mode = false;

    float dist_track= 0;

    int consecutive_iterations = 0;
    Handler user_handle = new Handler();

    // Animation methods.

    public void draw_ball() {

        // I want to keep the user inside this function.

        final ImageView ball_window = (ImageView) findViewById(R.id.ball_window);
        ball_window.setImageResource(R.drawable.ball);
        ball_window.setImageAlpha(255);
    }

    public void undraw_ball() {

        final ImageView ball_window = (ImageView) findViewById(R.id.ball_window);
        ball_window.setImageAlpha(0);
    }

    public void move_right(ImageView animate_walk) {

        float r_dist;
        int low = 100;
        int high = 750;
        final ImageView back_to_start = (ImageView) findViewById(R.id.animation_window);
        Random rand_dist = new Random();
        r_dist = (float) rand_dist.nextInt(high - low) + low;
        Handler handle = new Handler();
        Handler inside_if_handle = new Handler();
        if (r_dist < dist_track) {
            inside_if_handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    walk_left();
                }
            }, 250);
        }
        else {
            Log.d("Jake", "Moving Right >>> Random Float is: " + r_dist);
            Log.d("Jake", "Moving Right >>> dist track right is: " + dist_track);
            final ObjectAnimator move_to_right = ObjectAnimator.ofFloat(animate_walk, "translationX", r_dist);
            move_to_right.setDuration(1000);
            move_to_right.start();
            dist_track = r_dist;
            handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                back_to_start.setImageResource(R.drawable.start_dog);

            }
        }, 1200);
        }
    }

    public void move_left(ImageView animate_walk) {

        float r_dist;
        int low = 100;
        int high = 750;
        final ImageView back_to_start = (ImageView) findViewById(R.id.animation_window);
        Random rand_dist = new Random();
        Handler handle = new Handler();
        Handler inside_if_handle = new Handler();
        r_dist = (float) (rand_dist.nextInt(high - low) + low) * -1;
        if (r_dist > dist_track + 100) {
            inside_if_handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    walk_right();
                }
            }, 250);
        }
        else {
            Log.d("Jake", "Moving Left >>> Random Float is: " + r_dist);
            Log.d("Jake", "Moving Left >>> dist track left is: " + dist_track);
            final ObjectAnimator move_to_left = ObjectAnimator.ofFloat(animate_walk, "translationX", r_dist);
            move_to_left.setDuration(800);
            move_to_left.start();
            dist_track = r_dist;
            handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                back_to_start.setImageResource(R.drawable.start_dog);

            }
        }, 1200);
        }
    }

    public void walk_right() {

        final ImageView animate_walk = (ImageView) findViewById(R.id.animation_window);
        animate_walk.setImageResource(R.drawable.dog_walk_right);
        final AnimationDrawable walking_dog = (AnimationDrawable) animate_walk.getDrawable();

        walking_dog.start();
        Log.d("Jake", "\nStarted the MOVE RIGHT ANIMATION.\n");
        move_right(animate_walk);
        Log.d("Jake", "\nStarted MOVING ANIMATION TO THE RIGHT.\n");

    }

    public void walk_left() {

        final ImageView animate_walk = (ImageView) findViewById(R.id.animation_window);
        animate_walk.setImageResource(R.drawable.dog_walk);
        final AnimationDrawable walking_dog = (AnimationDrawable) animate_walk.getDrawable();
        final Handler handle = new Handler();

        walking_dog.start();
        Log.d("Jake", "\nStarted the MOVE LEFT ANIMATION.\n");
        move_left(animate_walk);
        Log.d("Jake", "\nStarted the MOVING ANIMATION LEFT.\n");

    }

    public void dead_toast() {

        // Load my final sad song into mediaplayer and then play.

        final MediaPlayer sad_song = MediaPlayer.create(this, R.raw.sad_violin);
        sad_song.start();

        Toast t1 = Toast.makeText(getApplicationContext(), "YO HE'S DEAD", Toast.LENGTH_LONG);
        t1.show();

        final AlertDialog.Builder fin = new AlertDialog.Builder(this);
        fin.setTitle("Game Over!");
        fin.setCancelable(false);
        fin.setMessage("OH NO! YOU KILLED JAKE! START AGAIN!");
        fin.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sad_song.stop();
                finish();
            }
        });
        fin.show();
    }

    public void dog_scratch() {

        final ImageView animate_scratch = (ImageView) findViewById(R.id.animation_window);
        Log.d("Jake", "GOT TO THE FUNCTION.");

        animate_scratch.setImageResource(R.drawable.dog_scratch);

        final AnimationDrawable scratching_dog = (AnimationDrawable) animate_scratch.getDrawable();

        final Handler time_delay = new Handler();

        scratching_dog.start();

        time_delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                animate_scratch.setImageResource(R.drawable.start_dog);
                fresh_dog.set_scracthing(false);
            }
        }, 1500);
    }

    // Here we need an @ statement because I want my app to go full screen.
    // This means it will not run on an API below 14.

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // The following code block sets my view parameters onCreate.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Now the rest of my app builds.

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fresh_dog = new jake(this);
        fresh_dog.come_alive();

        feed_button = findViewById(R.id.feed_button);
        water_button = findViewById(R.id.water_button);
        play_button = findViewById(R.id.play_button);
        clean_button = findViewById(R.id.clean_button);
        stats_button = findViewById(R.id.stats_button);

        final ImageView start_dog = (ImageView) findViewById(R.id.animation_window);
        start_dog.setImageResource(R.drawable.start_dog);

        // Set up initial time thread for non-user interaction.

        final time_thread time = new time_thread(fresh_dog, this);

        // Initialise the user-driven thred when the user presses play.

        final user_interaction_thread user_thread = new user_interaction_thread(fresh_dog, this);

        // Set up thread pool to deal with a single task, the time_thread.
        // When it is constructed we give it a task and run!

        // We must set specified method runOnUiThread to run methods in main from within thread.

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                time.run();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!fresh_dog.ask_alive()) {

                            dead_toast();
                        }
                    }
                });
            }
        });

        // Need to start time thread here which makes calls to the setter method in Jake to reduce
        // stats. Time-forced animations are fired from here.

        // This main onCreate method is the user-side of things where user-led animations are fired.


        // Setting onClick listener for FEED button.
        // At the moment this will put the feed stat up. Testing purposes only. Capped in future.

        feed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_dog.setImageResource(R.drawable.start_dog);

                Log.d("Jake", "FEED BUTTON PRESSED!");
                Log.d("Jake", "Jakes hunger is " + fresh_dog.getM_hunger());


                int hunger_thresh = fresh_dog.getM_hunger();

                if (hunger_thresh >= 250) {

                    fresh_dog.jake_explodes();
                    Toast explode_toast = Toast.makeText(getApplicationContext(), "You can't feed any more" +
                            "He exlpoded!", Toast.LENGTH_SHORT);

                    explode_toast.show();
                    dead_toast();

                } else {
                    Log.d("Jake", "Well Done! You fed Jake.");
                    fresh_dog.setM_hunger(3);

                    // TODO: Animation fucntion to be programmed below.
                    fresh_dog.jake_eats();

                    Toast t2 = Toast.makeText(getApplicationContext(), "Thanks!, You fed Jake :)",
                            Toast.LENGTH_SHORT);

                    t2.show();
                    Handler toast_handle = new Handler();
                    toast_handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Hunger to: " + fresh_dog.getM_hunger(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, 2700);
                }
            }

        });

        water_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Jake", "You pressed the WATER button.");
                Log.d("Jake", "The dogs thirst is: " + fresh_dog.getM_thirst());

                start_dog.setImageResource(R.drawable.start_dog);

                int thirst_thresh = fresh_dog.getM_thirst();

                if (thirst_thresh >= 250) {
                    Log.d("Jake", "oh No! Jake is drowning in Pee!");
                    fresh_dog.jake_drowns();
                    Toast explode_toast = Toast.makeText(getApplicationContext(), "You can't water any more" +
                            "He drowned!", Toast.LENGTH_SHORT);

                    explode_toast.show();
                    dead_toast();

                }else {

                    Log.d("Jake", "Well done you WATERED the dog.");

                    fresh_dog.setM_thirst(2);

                    //TODO: Animation fucntion below needs programmed.
                    fresh_dog.jake_drinks();

                    Toast t2 = Toast.makeText(getApplicationContext(), "Thanks!, You WATERED Jake :)",
                            Toast.LENGTH_SHORT);

                    t2.show();
                    Handler toast_handle = new Handler();
                    toast_handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Thirst to: " + fresh_dog.getM_thirst(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, 2700);



                }

            }
        });

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Could start this thread and keep time running so stats go up and down.
                // I need to call a method within time_thread to stop the animations.
                // If USER_SWITCH is true then skip all probabilistic animations and just count time.

                draw_ball();

                time.set_interacting(true);



            }
        });

        stats_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent stat_intent = new Intent(MainActivity.this, stat_screen_controller.class);
                stat_intent.putExtra("fresh_dog", fresh_dog);
                startActivity(stat_intent);

            }
        });
    }
}



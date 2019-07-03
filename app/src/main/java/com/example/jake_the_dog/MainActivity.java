package com.example.jake_the_dog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    jake fresh_dog;

    Button feed_button;
    Button water_button;
    Button play_button;
    Button clean_button;
    Button stats_button;

    // Global dead_toast function to be called from within onCreate.

    public void walk_dog() {

        final ImageView animate_walk = (ImageView) findViewById(R.id.animation_window);
        ObjectAnimator move_to_side = ObjectAnimator.ofFloat(animate_walk, "translationX", 1f);

        animate_walk.setImageResource(R.drawable.dog_walk);
        move_to_side.setDuration(2000);

        final AnimationDrawable walking_dog = (AnimationDrawable) animate_walk.getDrawable();

        walking_dog.start();
        move_to_side.start();

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

        fresh_dog = new jake();
        fresh_dog.come_alive();

        feed_button = findViewById(R.id.feed_button);
        water_button = findViewById(R.id.water_button);
        play_button = findViewById(R.id.play_button);
        clean_button = findViewById(R.id.clean_button);
        stats_button = findViewById(R.id.stats_button);

        final ImageView start_dog = (ImageView) findViewById(R.id.animation_window);
        start_dog.setImageResource(R.drawable.start_dog);

        final time_thread time = new time_thread(fresh_dog);

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

                walk_dog();

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



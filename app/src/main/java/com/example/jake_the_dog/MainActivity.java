package com.example.jake_the_dog;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.opengl.EGLObjectHandle;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ArcMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
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
    Button ball_button;

    float dist_track = 0;
    float ball_track = 0;

    float ball_pos;

    float parameter_x;
    float y_co;

    double x_cor;
    double y_cor;

    float x;
    float y;

    int pee_fence = 0;

    boolean play_mode = false;
    boolean is_peeing = false;
    Handler ball_wait = new Handler();

    // Animation methods.

//    public void change_pee_fence() {
//
//        Log.d("Jake", "[+] >>> REACHED CHANGE BACKGROUND");
//
//        final Handler mini_handle = new Handler();
//
//        final ImageView background = (ImageView) findViewById(R.id.test_background);
//
//        if(pee_fence < 1) {
//            pee_fence += 1;
//            mini_handle.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    background.setImageResource(R.drawable.the_garden_pee1);
//                    Log.d("Jake", "[+} CHANGED TO PEE 1");
//                }
//            }, 500);
//
//        }
//        if(pee_fence < 2) {
//
//            background.setImageResource(R.drawable.the_garden_pee2);
//            pee_fence += 1;
//            Log.d("Jake", "[+} CHANGED TO PEE 2");
//        }
//        if(pee_fence < 3){
//
//            background.setImageResource(R.drawable.the_garden_pee3);
//            Log.d("Jake", "[+} CHANGED TO PEE 3");
//            pee_fence += 1;
//
//        }
//
//    }

    public void dog_pees() {

        final int[] pee_img = {R.drawable.the_garden_pee1,
                R.drawable.the_garden_pee2,
                R.drawable.the_garden_pee3};

        final Handler piss_handle = new Handler();
        final Handler background_change_handle = new Handler();
        final ImageView background = (ImageView) findViewById(R.id.test_background);
        is_peeing = true;
        final ImageView dog_window = (ImageView) findViewById(R.id.animation_window);
        dog_window.setImageResource(R.drawable.dog_walk);
        final AnimationDrawable moving_dog = (AnimationDrawable) dog_window.getDrawable();
        moving_dog.start();
        dog_window.animate().x(180f).setDuration(500).start();
        piss_handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                dog_window.setImageResource(R.drawable.dog_pees);
                background_change_handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        background.setImageResource(R.drawable.the_garden_pee3);
                    }
                }, 500);
            }
        }, 600);
    }

    public void move_cloud_one () {

        final ImageView cloud_one_move = (ImageView) findViewById(R.id.cloud_one);
        final float end = cloud_one_move.getTranslationX() + 10;
        final ObjectAnimator move_cloud = ObjectAnimator.ofFloat(cloud_one_move, "translationX", end);
        if (end > 2050) {
            cloud_one_move.setX(-500);
        }
        else {
            move_cloud.setDuration(250);
            move_cloud.start();
        }
    }

    public void move_cloud_two() {

        final ImageView cloud_two_move = (ImageView) findViewById(R.id.cloud_two);
        final float end = cloud_two_move.getTranslationX() - 10;
        final ObjectAnimator move_cloud = ObjectAnimator.ofFloat(cloud_two_move, "translationX", end);
        if (end < - 1500) {
            cloud_two_move.setX(2100);
        }
        else {
            move_cloud.setDuration(250);
            move_cloud.start();
        }
    }

    public ImageView draw_ball() {

        final ImageView ball_window = (ImageView) findViewById(R.id.ball_window);
        ball_window.setImageResource(R.drawable.ball);
        ball_window.setX(211f);
        ball_window.setY(499f);
        Log.d("JakePlay", " Initial Ball X >> " + ball_window.getX());
        Log.d("JakePlay", " Initial Ball Y >> " + ball_window.getY());
        ball_window.setImageAlpha(255);
        return ball_window;
    }

    public void animate_ball(ImageView ball_window, boolean initial) {

        ball_window.setImageResource(R.drawable.ball_roll);
        final AnimationDrawable animate_ball = (AnimationDrawable) ball_window.getDrawable();
        if (initial) {
            animate_ball.start();
            drop_ball(ball_window);
        }
        else {
            animate_ball.start();
        }


    }

    public double get_throw_y(double x) {

        double x_sqaure;
        double x_multiply;
        double end = 157290;
        double y;

        x_sqaure = Math.sqrt(x);
        x_multiply = x * 960;
        y = x_sqaure - x_multiply + end;
        return  y;

    }

    public void throw_ball() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            final ImageView ball_window = (ImageView) findViewById(R.id.ball_window);

            Path ball_path = new Path();
            RectF ball_arc = new RectF(130f, 130f, 650f, 650f);
            ball_path.arcTo(ball_arc, 30, 270, true);
            PathInterpolator ball_path_inter = new PathInterpolator(ball_path);
            animate_ball(ball_window, false);
            ObjectAnimator throw_ball = ObjectAnimator.ofFloat(ball_window, "translationX",  0f, 500f).setDuration(2000);
            throw_ball.setInterpolator(ball_path_inter);
            throw_ball.start();
        }

        else {

            roll_ball();
        }
    }

    public void roll_ball() {

        Log.d("Jake", "Reached the function!");

        final Handler ball_handle = new Handler();
        final ImageView ball_window = (ImageView) findViewById(R.id.ball_window);
        animate_ball(ball_window, false);

        // ValueAnimator Attempt.

        ball_window.animate().x(1800f).y(700f).setDuration(1000)
                .start();
        ball_handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                ball_window.setImageResource(R.drawable.ball);
                Log.d("Jake", "Ball X Co-ordinate: " + ball_window.getX());
                Log.d("Jake", "Ball Y Co-ordinate: " + ball_window.getY());
                fetch_ball();

            }
        }, 1000);
    }

    public void drop_ball(final ImageView animating_ball) {

        final float end = animating_ball.getTranslationY() + 250;
        final Handler ball_delay = new Handler();
        ball_track = end;
        ObjectAnimator drop_ball = ObjectAnimator.ofFloat(animating_ball, "translationY", end);
        drop_ball.setDuration(500);
        drop_ball.start();
        ball_delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                animating_ball.setImageResource(R.drawable.ball);
                play_mode = true;
            }
        }, 500);

    }

    public void fetch_ball() {


        final ImageView ball_window = (ImageView) findViewById(R.id.ball_window);
        final Handler ball_handler = new Handler();
        final Handler inner_handler = new Handler();
        final Handler dropped_off_ball = new Handler();

        final ImageView getting_ball = (ImageView) findViewById(R.id.animation_window);

        getting_ball.setImageResource(R.drawable.dog_walk_right);

        final AnimationDrawable grab_ball = (AnimationDrawable) getting_ball.getDrawable();

        // This starts him moving right towards the ball.
        // TODO: Logic will go here when the ball goes to a random location.

        grab_ball.start();

        // The following animation call actually moves the "moving" dog across the screen. ]

        getting_ball.animate().x(1550f).setDuration(1000).start();

        // A one second delay for the ball to roll to the other side of the screen.

        ball_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                grab_ball.stop();
                ball_window.animate().y(650f).setDuration(100).start();
                inner_handler.postDelayed(new Runnable() {

                    // A 50 millisecond delay to "pick up" the ball.

                    @Override
                    public void run() {

                        // Slightly lift the ball window and switch it to his other side.

                        ball_window.setX(1450f);

                        // Set all my animation resources for waking left.

                        getting_ball.setImageResource(R.drawable.dog_walk);
                        final AnimationDrawable drop_ball = (AnimationDrawable) getting_ball.getDrawable();
                        getting_ball.animate().x(280f).setDuration(1000).start();
                        drop_ball.start();
                        ball_window.animate().x(211f).setDuration(1000).start();
                        dropped_off_ball.postDelayed(new Runnable() {

                            // An 1100 millisecond delay for the dog to take the ball back to its original location.

                            @Override
                            public void run() {

                                // Once back to its original spot reset X, Y and Alpha.

                                getting_ball.setImageResource(R.drawable.start_dog);
                                ball_window.setX(211f);
                                ball_window.setY(750f);
                                ball_window.setImageAlpha(255);
                            }
                        }, 1100);


                    }
                }, 50);
            }
        }, 1000);
    }

    public void undraw_ball() {

        final ImageView ball_window = (ImageView) findViewById(R.id.ball_window);
        ball_window.setImageAlpha(0);
        play_mode = false;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ball_wait.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ball_window.setX(211);
                        ball_window.setY(499);


                    }
                }, 100);
            }
        });
    }

    public void move_right(ImageView animate_walk) {

        final float end = animate_walk.getTranslationX() + 150;
        dist_track = end;
        ObjectAnimator move_dog = ObjectAnimator.ofFloat(animate_walk, "translationX", end);
        move_dog.setDuration(2000);
        move_dog.start();
    }

    public void move_left(ImageView animate_walk) {

        final float end = animate_walk.getTranslationX() - 150;
        dist_track = end;
        ObjectAnimator move_dog = ObjectAnimator.ofFloat(animate_walk, "translationX", end);
        move_dog.setDuration(2000);
        move_dog.start();

    }

    public void walk_right() {

        final ImageView animate_walk = (ImageView) findViewById(R.id.animation_window);
        animate_walk.setImageResource(R.drawable.dog_walk_right);
        final AnimationDrawable walking_dog = (AnimationDrawable) animate_walk.getDrawable();
        final Handler handle = new Handler();
        if (dist_track > 600) {
            walk_left();
        }
        else {
            walking_dog.start();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animate_walk.setImageResource(R.drawable.start_dog);
                    Log.d("Jake", "[+] Moving right >>> distance track is: " + dist_track);
                }
            }, 2000);
            move_right(animate_walk);
        }
    }

    public void walk_left() {

        final ImageView animate_walk = (ImageView) findViewById(R.id.animation_window);
        animate_walk.setImageResource(R.drawable.dog_walk);
        final AnimationDrawable walking_dog = (AnimationDrawable) animate_walk.getDrawable();
        final Handler handle = new Handler();
        if (dist_track < -600) {
            walk_right();
        }
        else {
            walking_dog.start();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animate_walk.setImageResource(R.drawable.start_dog);
                    Log.d("Jake", "[+] Moving left <<< distance track is: " + dist_track);
                }
            }, 2000);
            move_left(animate_walk);
        }
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
        scratching_dog.start();

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
        ball_button = findViewById(R.id.ball_button);

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
                    Log.d("Jake", "Well Done! You fed the dog :)");
                    fresh_dog.setM_hunger(3);

                    // TODO: Animation function to be programmed below.
                    // TODO: Time_thread must get "dog_is_eating" flag to skip arbitrary animations.

                    fresh_dog.jake_eats();

                    Toast t2 = Toast.makeText(getApplicationContext(), "Thanks!, You fed the dog :)",
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

                int thirst_thresh = fresh_dog.getM_thirst();

                if (thirst_thresh >= 250) {
                    Log.d("Jake", "oh No! Jake is drowning in Pee!");
                    fresh_dog.jake_drowns();
                    Toast explode_toast = Toast.makeText(getApplicationContext(), "You can't water any more" +
                            "He drowned!", Toast.LENGTH_SHORT);

                    explode_toast.show();
                    dead_toast();

                }else {

                    Log.d("Jake", "Well done you watered the dog :)");

                    fresh_dog.setM_thirst(2);

                    fresh_dog.jake_drinks();

                    Toast t2 = Toast.makeText(getApplicationContext(), "Thanks!, You watered the dog :)",
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

                // I need to call a method within time_thread to stop the animations.
                // If USER_SWITCH is true then skip all probabilistic animations and just count time.

                ImageView ball_window = draw_ball();
                animate_ball(ball_window, true);
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

        ball_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Add a listener for a prolonged press. If we detect a long press then
                // TODO: the ball will be thrown rather than rolled.

                if(play_mode) {

                    Log.d("JakePlay", "We are now triggering button in Play Mode");
                    roll_ball();
                    time.reset_cont_iter();

                }
            }
        });

        clean_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Clean the fence please!", Toast.LENGTH_LONG)
                        .show();

            }
        });
    }
}



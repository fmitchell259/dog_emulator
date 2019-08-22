package com.example.jake_the_dog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class stat_screen_controller extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int print_hung,
                print_thirst,
                print_clean,
                print_bored;

        // The following code block sets my view parameters onCreate.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Then we launch into the regular set-up calls for onCreate().

        super.onCreate(savedInstanceState);

        setContentView(R.layout.stats_screen);

        // Set my back_button and dog_reference straight away.

        final TextView hunger_field = (TextView) findViewById(R.id.hunger_show_text);
        final TextView thirst_field = (TextView) findViewById(R.id.thirst_show_text);
        final TextView bored_field = (TextView) findViewById(R.id.bored_show_text);
        final TextView clean_field = (TextView) findViewById(R.id.clean_show_text);

        Button back_button = (Button) findViewById(R.id.back_button);

        jake fresh_dog = getIntent().getExtras().getParcelable("fresh_dog");

        // Next retrieve all of the dogs stats with an @Nullable decorator to ensure
        // nullPointerException is covered.

        @Nullable
        double fresh_hung = fresh_dog.getM_hunger();
        @Nullable
        double fresh_thirst = fresh_dog.getM_thirst();
        @Nullable
        double fresh_bored = fresh_dog.getM_bored();
        @Nullable
        double fresh_clean = fresh_dog.getM_clean();

        print_hung = (int) fresh_hung;
        print_thirst = (int) fresh_thirst;
        print_bored = (int) fresh_bored;
        print_clean = (int) fresh_clean;

        Log.d("Jake", "Hunger is: " + fresh_hung);

        hunger_field.setText("" + print_hung);
        thirst_field.setText(" " + print_thirst);
        bored_field.setText(" " + print_bored);
        clean_field.setText(" " + print_clean);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

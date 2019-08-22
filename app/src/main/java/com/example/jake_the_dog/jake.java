package com.example.jake_the_dog;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


public class jake implements Parcelable {


    protected jake(Parcel in) {
        m_hunger = in.readDouble();
        m_thirst = in.readDouble();
        m_clean = in.readDouble();
        m_bored = in.readDouble();
        m_tired = in.readDouble();
        is_alive = in.readByte() != 0;
    }

    // We need to "parcel" the dog class in order to move it between Activities and save stats.

    public static final Creator<jake> CREATOR = new Creator<jake>() {
        @Override
        public jake createFromParcel(Parcel in) {
            return new jake(in);
        }

        @Override
        public jake[] newArray(int size) {
            return new jake[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(m_hunger);
        dest.writeDouble(m_thirst);
        dest.writeDouble(m_clean);
        dest.writeDouble(m_bored);
        dest.writeDouble(m_tired);
        dest.writeInt( is_alive ? 1: 0);
    }

    // End of Parcelable methods needed in order to implement Parcelable.

    // Beginning of declarations of member variables and methods for jake class.

    private double m_hunger, m_thirst,
                m_clean, m_bored,
                m_tired;

    private boolean dog_is_scratching = false;

    private MainActivity act;

    private boolean is_alive;

    // Basic constructor for jake, he starts with 100.

    // Main activity is passed a parameter in order to call the animation methods stored there.

    jake(MainActivity parent_active) {
        m_hunger = 100;
        m_bored = 100;
        m_clean = 100;
        m_thirst = 100;
        m_tired = 100;
        act = parent_active;


    }

    // Getter and Setter methods for each member stat.

    public boolean ask_alive() {
        return this.is_alive;
    }

    public void set_scracthing(boolean scratching) {
        dog_is_scratching = scratching;
    }

    public boolean get_scratching() {
        return dog_is_scratching;
    }

    public double getM_hunger() {
        return m_hunger;
    }

    public double getM_thirst() {
        return m_thirst;
    }

    public double getM_clean() {
        return m_clean;
    }

    public double getM_bored() {
        return m_bored;
    }


    public double getM_tired() {
        return m_tired;
    }

    public void setM_hunger(double m_hunger) {

            this.m_hunger += m_hunger;

    }

    public void setM_thirst(double m_thirst) {
        this.m_thirst += m_thirst;
    }

    public void setM_clean(double m_clean) {
        this.m_clean += m_clean;
    }

    public void setM_bored(double m_bored) {
        this.m_bored += m_bored;
    }


    // Set Tired is only going to be used when NO USER INTERACTION occurs.

    public void setM_tired(int m_tired) {
        this.m_tired += m_tired;
    }


    // Animation methods.

    public void jake_explodes() {

        Log.d("Jake", "Well DONE JAKE EXPLODED!!!");


    }

    public void jake_dead() {
        this.is_alive = false;
        Log.d("Jake", "Oh No your dog DIED!");

    }

    public void come_alive() {

        is_alive = true;

        // TODO: 2019-06-06 Add animation for this method. This should NOT affect the stats. It is
        //                  purley to make jake move. These stats are looked after in the time thread.

    }

    public void jake_drowns() {

        //TODO: This animation fires when Jake gets WATERED FAR TOO MUCH.

        this.is_alive = false;
        Log.d("Jake", "He drowned in pee :(");
    }

    public void jake_plays() {

        // TODO: 2019-06-06 Jake plays here, this should directly affect his stats. However, this
        //                  should not be adjusted here. Within the time thread, while jake is "playing"
        //                  his stats should reflect that. This method should just launch his animation.

    }

    public void jake_eats() {

        // TODO: 2019-06-06 Animate Jake eating, make the call to effect stats within the time thread.
        //                  The time thread will count how many seconds each animation is fired for.
        //                  I may need to add a parameter to these functions (time).
    }

    public void jake_drinks() {

        // TODO: 2019-06-06 Similiar deal here, the time thread will count how long to animate for.
        //                  That thread will also call the method to adjust stats accordingly.
    }

    public void clean_jake() {

        // TODO: This could be an interactive animation where you swipe the creen back and forth to
        //       to clean Jake. Stats adjusted by the time thread.
    }

    public  void jake_learns() {

        // TODO: This is a future update where we can train the dogs from puppies to do different
        //       tricks. Such as fetch, roll over and lie down, all using gestures.
    }

    public void walk_dog_left() {

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.walk_left();
            }
        });

    }

    public void walk_dog_right() {

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.walk_right();
            }
        });

    }

    public void dog_scratches() {

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.dog_scratch();
            }
        });
    }


}

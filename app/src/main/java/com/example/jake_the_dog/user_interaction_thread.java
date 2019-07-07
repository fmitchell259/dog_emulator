package com.example.jake_the_dog;

import android.util.Log;

public class user_interaction_thread implements Runnable {

    private jake passed_dog;
    private MainActivity m_act;
    private boolean interacting;

    user_interaction_thread(jake p_dog, MainActivity parent_activity) {

        passed_dog = p_dog;
        m_act = parent_activity;

    }

    public void run() {

        Log.d("Jake", "Entered User interaction thread.");
        Log.d("Jake", "Drawing ball....");
        m_act.draw_ball();

    }

    public void set_interacting(boolean y) {

        interacting = y;
    }

    public boolean get_interacting() {
        return interacting;
    }

}

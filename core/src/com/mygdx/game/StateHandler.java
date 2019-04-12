package com.mygdx.game;

import java.util.Vector;

// a class that manages States (specifically the one on top of the vector

public class StateHandler extends Vector<State> {
    public void update(float gameLoopTime){
        lastElement().update(gameLoopTime);
    }

    public void set(PlayState playState) {
        set(size(),playState);
    }
}

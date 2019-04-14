package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

// A "template" for any state the program might be in eg. Pause, Play

public abstract class State {
    OrthographicCamera camera;
    StateHandler sh;
    protected State(StateHandler sh){
        this.sh=sh;
    }
    public abstract void update(float gameLoopTime); //a method that updates the state, takes float value of time passed
}                                                    // in an instance of a game loop to compensate for differences in speed of different machines

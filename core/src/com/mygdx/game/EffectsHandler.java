package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.util.Vector;

public class EffectsHandler extends Thread {
    Vector<Effect> effects = new Vector<Effect>();
    Vector<Effect> tempor = new Vector<Effect>();
    StateHandler stateHandler;

    EffectsHandler(){
        setDaemon(true);
    }
    void setUp(StateHandler stateHandler){
        this.stateHandler=stateHandler;
    }
    @Override
    public void run(){
        while (true) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(Effect i: effects){
                if(i!=null)
                    i.calculate(Gdx.graphics.getDeltaTime());
            }
            effects.addAll(tempor);
            tempor.clear();
        }
    }
}

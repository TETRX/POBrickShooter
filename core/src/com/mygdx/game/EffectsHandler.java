package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.util.Vector;

public class EffectsHandler extends Thread {
    Vector<Effect> effects = new Vector<Effect>();
    Vector<Effect> tempor = new Vector<Effect>();
    Vector<Effect> remove = new Vector<Effect>();
    StateHandler stateHandler;

    //BackgroundEffect backgroundEffect = new BackgroundEffect(this);

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
                if(i!=null) {
                    if(!i.calculate(Gdx.graphics.getDeltaTime())){
                        i.finish();
                    }
                }
            }
            effects.addAll(tempor);
            tempor.clear();
            while(!remove.isEmpty()){
                effects.remove(remove.lastElement());
                remove.remove(remove.lastElement());
            }
            //backgroundEffect.calculate(Gdx.graphics.getDeltaTime());
        }
    }
}

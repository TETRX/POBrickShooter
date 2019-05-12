package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Effect {

    EffectsHandler effectsHandler;

    Effect(EffectsHandler effectsHandler){
        this.effectsHandler=effectsHandler;
    }

    public static Pixmap pixmap=new Pixmap(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), Pixmap.Format.RGB888);
    static {
        pixmap.setBlending(Pixmap.Blending.None);
    }

    public abstract boolean calculate(float deltaTime);

    public void finish(){
        effectsHandler.remove.add(this);
    }

}

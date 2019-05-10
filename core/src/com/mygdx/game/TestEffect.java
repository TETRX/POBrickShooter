package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

//A Class to test the functionality of Effect class and infrastructure behind it
// NOT part of the final product

public class TestEffect extends Effect{
    Random r = new Random();

    TestEffect(EffectsHandler effectsHandler) {
        super(effectsHandler);
    }

    @Override
    public boolean calculate(float deltaTime) {
        pixmap.setColor(Color.SLATE);
        for(int i=0;i<300; i++){
            pixmap.drawPixel(r.nextInt(Gdx.graphics.getWidth()),r.nextInt(Gdx.graphics.getHeight()));
        }
        return true;
    }

}

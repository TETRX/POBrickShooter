package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class RandomColor implements Coloring {

    Random r= new Random();

    @Override
    public Color color() {
        return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f);
    }
}

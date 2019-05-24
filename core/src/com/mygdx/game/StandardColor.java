package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class StandardColor implements Coloring {

    final Color color;

    StandardColor(Color color){
        this.color=color;
    }

    @Override
    public Color color() {
        return color;
    }
}

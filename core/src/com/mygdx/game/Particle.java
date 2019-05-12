package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Particle {
    Vector2 position;
    Vector2 movement;

    Particle(Vector2 position, Vector2 movement){
        this.position=new Vector2();
        this.movement=new Vector2();
        if(position!=null)
            this.position.set(position);
        this.movement.set(movement);
    }

    void move(float deltaTime){
        position.add(new Vector2(movement.x*deltaTime*5,movement.y*deltaTime*5));
    }

    public boolean isOutOfBounds(){
        return position.x < 0 || position.y < 0 || position.y > Gdx.graphics.getHeight() || position.x > Gdx.graphics.getWidth();
    }

}

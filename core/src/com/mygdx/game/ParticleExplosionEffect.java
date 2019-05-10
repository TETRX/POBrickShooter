package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class ParticleExplosionEffect extends Effect {

    Random r;
    Particle []particles;

    ParticleExplosionEffect(EffectsHandler effectsHandler, Vector2 where){
        super(effectsHandler);
        r=new Random();
        particles=new Particle[3000];
        for(int i=0;i<3000;i++){
            particles[i]=new Particle(new Vector2(where),
                    new Vector2((r.nextBoolean() ? -1: 1)*r.nextInt(Gdx.graphics.getHeight()),(r.nextBoolean() ? -1: 1)*r.nextInt(Gdx.graphics.getHeight())));
        }
    }

    @Override
    public boolean calculate(float deltaTime) {
        boolean ret=false;
        for(int i=0;i<3000;i++) {
            pixmap.setColor(Color.CLEAR);
            pixmap.drawPixel((int)particles[i].position.x,(int)particles[i].position.y);
            particles[i].move(deltaTime);
            if(!particles[i].isOutOfBounds()){
                ret=true;
            }
            pixmap.setColor(effectsHandler.stateHandler.settings.brickColor);
            pixmap.drawPixel((int)particles[i].position.x,(int)particles[i].position.y);
        }
        return ret;
    }
}

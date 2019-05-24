package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class ParticleExplosionEffect extends Effect {

    Random r;
    Particle []particles;
    int timer;

    ParticleExplosionEffect(EffectsHandler effectsHandler, Vector2 where, Texture texture){
        super(effectsHandler);
        r=new Random();
        particles = new Particle[texture.getHeight()*texture.getWidth()];
        for(int i=0; i<texture.getHeight();i++){
            for(int j=0; j<texture.getWidth();j++){
                particles[i*texture.getWidth()+j]= new Particle(new Vector2(where.x+i,where.y+j),new Vector2((r.nextBoolean() ? -1 : 1)*(r.nextInt(Gdx.graphics.getHeight())),
                        (r.nextBoolean() ? -1 : 1)*(r.nextInt(Gdx.graphics.getWidth()))));
            }
        }
    }

    @Override
    public boolean calculate(float deltaTime) {
       /* boolean ret=false;
        for(int i=0;i<3000;i++) {
            pixmap.setColor(Color.CLEAR);
            pixmap.drawPixel((int) particles[i].position.x, (int) particles[i].position.y);
            particles[i].move(deltaTime);
            if (!particles[i].isOutOfBounds()) {
                ret = true;
            }
            pixmap.setColor(effectsHandler.stateHandler.settings.brickColor);
            pixmap.drawPixel((int) particles[i].position.x, (int) particles[i].position.y);
        }
        timer++;
        if(timer>100){
            return false;
        }
        return ret;*/

        boolean ret=false;
        for(int i=0;i<50;i++) {
            pixmap.setColor(Color.CLEAR);
            pixmap.fillRectangle((int) particles[i].position.x, (int) particles[i].position.y,8,8);
            //pixmap.drawPixel((int) particles[i].position.x, (int) particles[i].position.y);
            particles[i].move(deltaTime);
            if (!particles[i].isOutOfBounds()) {
                ret = true;
            }
            pixmap.setColor(effectsHandler.stateHandler.settings.explosionEffectColor.color());
            pixmap.fillRectangle((int) particles[i].position.x, (int) particles[i].position.y,8,8);
            //pixmap.drawPixel((int) particles[i].position.x, (int) particles[i].position.y);
        }
        timer++;
        if(timer>100){
            return false;
        }
        return ret;
    }

    @Override
    public void finish(){
        pixmap.setColor(Color.CLEAR);
        pixmap.fill();
        super.finish();
    }
}

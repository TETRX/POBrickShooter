package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

public class TwinkleEffect extends Effect {

    static Timer timer = new Timer();
    boolean wasCr=false;

    private class Sparkle extends Particle{

        final Vector2 gravity=new Vector2(0,1).scl(Gdx.graphics.getDeltaTime()*100);
        Color color;
        float onlySoMuch=0f;

        Sparkle(Vector2 position, Vector2 movement, Color color) {
            super(position, movement);
            if(position!=null) {
                this.position.set(new Vector2(position.x, Gdx.graphics.getHeight()-position.y));
            }
            this.color=color;
        }

        @Override
        void move(float deltaTime) {
            super.move(deltaTime);
            movement.add(gravity);
            onlySoMuch+=deltaTime;
        }
    }

    Bullet bullet;
    Sparkle particles[];
    Random r;

    TwinkleEffect(EffectsHandler effectsHandler, Bullet bullet) {
        super(effectsHandler);
        r=new Random();
        this.bullet=bullet;
        particles= new Sparkle[100];
        for(int i=0; i<100; i++){
            particles[i]=null;
        }
        effectsHandler.tempor.add(this);
    }

    @Override
    public boolean calculate(float deltaTime) {
        for (int i = 0; i < 100; i++) {
            if (!wasCr && particles[i] == null) {
                if (r.nextBoolean()) {
                    particles[i] = new Sparkle(bullet.bulletPosition, new Vector2((r.nextBoolean() ? -1 : 1) * r.nextInt(50) * r.nextFloat(), (r.nextBoolean() ? -1 : 1) * r.nextInt(50) * r.nextFloat())
                            , effectsHandler.stateHandler.settings.trailEffectColor.color());
                    wasCr = true;
                    timer.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            wasCr = false;
                        }
                    }, 0.01f);
                }
            }
            if ((particles[i] != null && particles[i].position != null && !particles[i].isOutOfBounds()) && bullet.started) {
                pixmap.setColor(Color.CLEAR);
                pixmap.fillRectangle((int)particles[i].position.x,(int)particles[i].position.y,effectsHandler.stateHandler.settings.trailParticleSize,effectsHandler.stateHandler.settings.trailParticleSize);
                //pixmap.drawPixel((int) particles[i].position.x, (int) particles[i].position.y);
                particles[i].move(deltaTime);
                pixmap.setColor(particles[i].color);
                pixmap.fillRectangle((int)particles[i].position.x,(int)particles[i].position.y,effectsHandler.stateHandler.settings.trailParticleSize,effectsHandler.stateHandler.settings.trailParticleSize);
                // System.out.println(particles[i].position);
                if (particles[i].onlySoMuch > 1.0f || particles[i].isOutOfBounds()) {
                    pixmap.setColor(Color.CLEAR);
                    pixmap.fillRectangle((int)particles[i].position.x,(int)particles[i].position.y,effectsHandler.stateHandler.settings.trailParticleSize,effectsHandler.stateHandler.settings.trailParticleSize);
                    particles[i] = null;
                }
            }
            if (particles[i] != null && (particles[i].onlySoMuch > 1.0f || particles[i].isOutOfBounds() || !bullet.started)) {
                pixmap.setColor(Color.CLEAR);
                pixmap.fillRectangle((int)particles[i].position.x,(int)particles[i].position.y,effectsHandler.stateHandler.settings.trailParticleSize,effectsHandler.stateHandler.settings.trailParticleSize);
                particles[i] = null;
            }
        }
        return true;
    }
}

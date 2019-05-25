package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class EffectsSettingsMenuState extends EffectsSettingsState {

    Button trail;
    Button explosion;

    EffectsSettingsMenuState(StateHandler stateHandler) {
        super(stateHandler);
        trail= new TextButton("Bullet Trail", skin);
        trail.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        trail.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(trail);


        explosion= new TextButton("Block Explosion", skin);
        explosion.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        explosion.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(explosion);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float gameLoopTime) {
        super.update(gameLoopTime);
        sh.batch.end();
        stage.draw();
        if(trail.isPressed()){
            pres.setStarted(false);
            sh.remove(this);
            sh.add(new TransitionState(sh,new TrailEffectsSettingState(sh)));
        }
        if(explosion.isPressed()){
            pres.setStarted(false);
            sh.remove(this);
            sh.add(new TransitionState(sh,new ExplosionEffectsSettingState(sh)));
        }
        sh.batch.begin();
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class EffectsSettingsMenuState extends EffectsSettingsState {

    Button trail;
    Button explosion;
    Button back;

    EffectsSettingsMenuState(StateHandler stateHandler) {
        super(stateHandler);
        trail= new TextButton("Bullet Trail", skin);
        trail.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        trail.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(trail);

        back= new TextButton("back", skin);
        back.setPosition( Gdx.graphics.getWidth()*1/20,Gdx.graphics.getHeight()/40);
        back.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(back);

        explosion= new TextButton("Block Explosion", skin);
        explosion.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        explosion.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(explosion);


        Gdx.input.setInputProcessor(stage);
    }

    boolean a;

    void restore(){
        trail= new TextButton("Bullet Trail", skin);
        trail.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        trail.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(trail);

        back= new TextButton("back", skin);
        back.setPosition( Gdx.graphics.getWidth()*1/20,Gdx.graphics.getHeight()/40);
        back.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(back);

        explosion= new TextButton("Block Explosion", skin);
        explosion.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        explosion.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(explosion);
        pres.setStarted(true);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float gameLoopTime) {
        if(a){
            a=false;
            restore();
        }
        super.update(gameLoopTime);
        sh.batch.end();
        stage.draw();
        if(trail.isPressed()){
            pres.setStarted(false);
            a=true;
            sh.add(new TransitionState(sh,new TrailEffectsSettingState(sh)));
        }
        if(explosion.isPressed()){
            pres.setStarted(false);
            a=true;
            sh.add(new TransitionState(sh,new ExplosionEffectsSettingState(sh)));
        }
        if(back.isPressed()){
            Effect.pixmap.setColor(Color.CLEAR);
            Effect.pixmap.fill();
            pres.setStarted(false);
            stage.clear();
            sh.remove(this);
        }
        sh.batch.begin();
    }

}

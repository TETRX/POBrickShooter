package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ChangeLevelState extends State{

    StateHandler sh;
    Stage stage;
    Skin skin;
    Button easy;
    Button medium;
    Button hard;
    Button toMenu;

    ChangeLevelState(StateHandler sh){
        this.sh=sh;
        stage= new Stage();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        // skin=new Skin(Gdx.files.internal("uiskin.json"));
        easy = new TextButton("easy", skin);
        easy.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        easy.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(easy);
        hard = new TextButton("hard", skin);
        hard.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*2f/6f);
        hard.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(hard);
        medium= new TextButton("medium", skin);
        medium.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        medium.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(medium);
        toMenu= new TextButton("menu", skin);
        toMenu.setPosition( Gdx.graphics.getWidth()*17/20,Gdx.graphics.getHeight()/40);
        toMenu.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(toMenu);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void update(float gameLoopTime) {

        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(toMenu.isPressed()){
            sh.add(new TransitionState(sh,new MenuState(sh)));
        }
        if(easy.isPressed()){
            sh.settings.level=1;
            sh.remove(this);
            sh.add(new TransitionState(sh,new SettingsState(sh)));
        }
        if(medium.isPressed()){
            sh.settings.level=2;
            sh.remove(this);
            sh.add(new TransitionState(sh,new SettingsState(sh)));
        }
        if(hard.isPressed()){
            sh.settings.level=3;
            sh.remove(this);
            sh.add(new TransitionState(sh,new SettingsState(sh)));
        }

    }
}

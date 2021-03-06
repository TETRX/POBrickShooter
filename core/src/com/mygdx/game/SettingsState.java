package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Vector;


public class SettingsState extends State{
    Stage stage;
    Skin skin;
    StateHandler sh;

    Button changeColours;
    Button changeLevel;
    Button toMenu;
    Button back;
    void createButtons(){
        stage= new Stage();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        // skin=new Skin(Gdx.files.internal("uiskin.json"));
        changeColours = new TextButton("Change Colours", skin);
        changeColours.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        changeColours.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(changeColours);

        changeLevel = new TextButton("Change level", skin);
        changeLevel.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        changeLevel.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(changeLevel);

        toMenu= new TextButton("menu", skin);
        toMenu.setPosition( Gdx.graphics.getWidth()*17/20,Gdx.graphics.getHeight()/40);
        toMenu.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(toMenu);
        Gdx.input.setInputProcessor(stage);

        back= new TextButton("back", skin);
        back.setPosition( Gdx.graphics.getWidth()*1/20,Gdx.graphics.getHeight()/40);
        back.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(back);

    }

    protected SettingsState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        createButtons();

    }

    boolean a;


    @Override
    public void update(float gameLoopTime) {
        if(a){
            a=false;
            createButtons();
        }
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(toMenu.isPressed()){
            sh.remove(this);
        }
        if(changeColours.isPressed()){
            a=true;
            sh.add(new TransitionState(sh,new EffectsSettingsMenuState(sh)));
        }
        if(changeLevel.isPressed()){
            a=true;
            sh.add(new TransitionState(sh,new ChangeLevelState(sh)));
        }
        if(back.isPressed()){
            sh.remove(this);
        }
    }
}

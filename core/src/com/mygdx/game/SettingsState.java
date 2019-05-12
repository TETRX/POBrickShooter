package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class SettingsState extends State{
    Stage stage;
    Skin skin;
    StateHandler sh;

    Button changeColours;
    Button changeLevel;
    void createButtons(){
        stage= new Stage();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        // skin=new Skin(Gdx.files.internal("uiskin.json"));
        changeColours = new TextButton("Change Colours", skin);
        changeColours.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        changeColours.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(changeColours);

        changeLevel = new TextButton("Change level", skin);
        changeLevel.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*2f/6f);
        changeLevel.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(changeLevel);
        Gdx.input.setInputProcessor(stage);
    }

    protected SettingsState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        createButtons();

    }
    @Override
    public void update(float gameLoopTime) {
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(changeColours.isPressed()){
            sh.add(new TransitionState(sh,new ChangeColourState(sh)));
        }
        if(changeLevel.isPressed()){
            sh.add(new TransitionState(sh,new ChangeLevelState(sh)));
        }
    }
}

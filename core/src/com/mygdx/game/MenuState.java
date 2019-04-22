package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuState extends State {
    Stage stage;
    Skin skin;
    Button newGame;
    protected MenuState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        stage= new Stage();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
       // skin=new Skin(Gdx.files.internal("uiskin.json"));
        newGame = new TextButton("New Game", skin);
        newGame.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        newGame.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(newGame);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void update(float gameLoopTime) {
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(newGame.getClickListener().isPressed()){
            sh.add(new TransitionState(sh));
        }
    }
}

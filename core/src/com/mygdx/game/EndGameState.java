package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class EndGameState extends State {
    Stage stage;
    Skin skin;
    Button newGame;

    protected EndGameState(StateHandler sh) {
        super(sh);
        stage= new Stage();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        // skin=new Skin(Gdx.files.internal("uiskin.json"));
        newGame = new TextButton("Menu", skin);
        newGame.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        newGame.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(newGame);
        Gdx.input.setInputProcessor(stage);
    }
    BitmapFont font=new BitmapFont();

    @Override
    public void update(float gameLoopTime) {
        font.draw(sh.batch,"GAME OVER",320,150);
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(newGame.getClickListener().isPressed()){
            sh.add(new MenuState(sh));
        }
    }
}

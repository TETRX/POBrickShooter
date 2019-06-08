package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static java.lang.Thread.sleep;

public class MenuState extends State {
    Stage stage;
    Skin skin;
    Button newGame;
    Button continueGame;
    Button settings;
    Button exit;
    protected MenuState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        stage= new Stage();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        newGame = new TextButton("New Game", skin);
        newGame.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        newGame.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(newGame);
        continueGame = new TextButton("Continue Game", skin);
        continueGame.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*2f/6f);
        continueGame.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(continueGame);
        settings = new TextButton("Settings", skin);
        settings.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        settings.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(settings);
        exit = new TextButton("Exit Game", skin);
        exit.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*1f/6f);
        exit.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(exit);
        Gdx.input.setInputProcessor(stage);
    }

    boolean a;

    public void restore(){
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        newGame = new TextButton("New Game", skin);
        newGame.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        newGame.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(newGame);
        continueGame = new TextButton("Continue Game", skin);
        continueGame.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*2f/6f);
        continueGame.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(continueGame);
        settings = new TextButton("Settings", skin);
        settings.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        settings.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(settings);
        exit = new TextButton("Exit Game", skin);
        exit.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*1f/6f);
        exit.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(exit);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float gameLoopTime) {
        if(a){
            a=false;
            restore();
        }
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        //----new game
        if(newGame.getClickListener().isPressed()){
            a=true;
            sh.add(new TransitionState(sh,new WaitState(sh)));
        }

        //----------continue game
        if(continueGame.getClickListener().isPressed()){
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WaitState waitState=null;
            FileInputStream fileIS=null;
            ObjectInputStream inputStream = null;
            try {
                fileIS=new FileInputStream("lastGame.txt");
                inputStream = new ObjectInputStream(fileIS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                waitState=(WaitState)inputStream.readObject();
                fileIS.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            waitState.sh=sh;
            waitState.continueGame();
            sh.remove(this);
            sh.add(new TransitionState(sh,waitState));

        }

        if (exit.isPressed()){
            Gdx.app.exit();
        }

        //----------settings
        if(settings.getClickListener().isPressed()){
            a=true;
            sh.add(new TransitionState(sh,new SettingsState(sh)));
        }
    }
}

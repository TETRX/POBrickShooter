package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.io.*;


public class EndGameState extends State {
    Stage stage;
    Skin skin;
    Button newGame;
    Integer result,round,bestResult=0;

    protected EndGameState(StateHandler sh,int result, int round) {
        super(sh);
        this.result=result;
        this.round=round;
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
        font.draw(sh.batch,"GAME OVER",320,300);

        FileInputStream fileIS=null;
        ObjectInputStream inputStream = null;
        try {
            fileIS=new FileInputStream("bestScore.txt");
            inputStream = new ObjectInputStream(fileIS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bestResult=(Integer)inputStream.readObject();
            fileIS.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        font.draw(sh.batch,"your result: "+result+" round: "+round+" best score: "+bestResult,320,200);


        if(result>bestResult) {
            System.out.println("best");
            ObjectOutputStream outputStream = null;
            try {
                FileOutputStream fileOS = new FileOutputStream("bestScore.txt");
                outputStream = new ObjectOutputStream(fileOS);
                outputStream.writeObject(result);
                fileOS.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(newGame.getClickListener().isPressed()){
            System.out.println("setgbhsdgtbsgf");
            sh.add(new MenuState(sh));
        }
    }
}

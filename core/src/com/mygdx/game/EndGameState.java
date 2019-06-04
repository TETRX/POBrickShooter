package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

        font.getData().setScale(3*Gdx.graphics.getWidth()/600,3*Gdx.graphics.getWidth()/600);
        GlyphLayout f = new GlyphLayout();
        f.setText(font,"GAME OVER");
        font.draw(sh.batch,"GAME OVER",Gdx.graphics.getWidth()/2-f.width/2,400f/600f*Gdx.graphics.getWidth());

        FileInputStream fileIS=null;
        ObjectInputStream inputStream = null;
        File bestscore = new File("bestScore.txt");
        if(bestscore.exists()) {
            try {
                fileIS = new FileInputStream("bestScore.txt");
                inputStream = new ObjectInputStream(fileIS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bestResult = (Integer) inputStream.readObject();
                fileIS.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            bestResult=0;
        }
        font.getData().setScale(2*Gdx.graphics.getWidth()/600,2*Gdx.graphics.getWidth()/600);
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,"your result: "+result+" round: "+round);
        GlyphLayout glyphLayout2 = new GlyphLayout();
        glyphLayout2.setText(font," best score: "+bestResult);
        font.draw(sh.batch,"your result: "+result+" round: "+round,Gdx.graphics.getWidth()/2-glyphLayout.width/2,260f/600f*Gdx.graphics.getWidth());
        font.draw(sh.batch," best score: "+bestResult,Gdx.graphics.getWidth()/2-glyphLayout2.width/2,200f/600f*Gdx.graphics.getWidth());


        if(result>bestResult) {
            System.out.println("best");
            ObjectOutputStream outputStream = null;
            try {
                if(!bestscore.exists()){
                    bestscore.createNewFile();
                }
                FileOutputStream fileOS = new FileOutputStream(bestscore);
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

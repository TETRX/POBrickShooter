package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class WaitState extends State implements Serializable {
    protected WaitState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(sh.batch.getProjectionMatrix());
        createBlocks();
        createButtons();
        listOfBullets.add(new Bullet(this));
    }
    WaitState(){

    }
    transient ShapeRenderer shapeRenderer;
   transient StateHandler sh;
    Vector2 start=new Vector2(400,10);
    Vector2 destination=new Vector2();
    Vector2 velocity;
    List<Bullet> listOfBullets=new ArrayList<Bullet>();
    Block [][] arrOfBlocks=new Block[5][5];
    int round=1;
    int result=0;
    Random rand = new Random();

    float allBlocksX;
    float allBlocksY;
    float allBlocksHeight;
    float allBlocksWidth;
   transient BitmapFont font=new BitmapFont();

   void continueGame(){
       font=new BitmapFont();
       shapeRenderer=new ShapeRenderer();
       createButtons();
       for(Bullet b : listOfBullets)
           b.continueGame();
       for(int i=0;i<5;i++){
           for(int j=0;j<5;j++){
               arrOfBlocks[i][j].continueGame();
           }
       }
   }
    void createBlocks(){
        allBlocksX=Gdx.graphics.getWidth()/16f; //50
        allBlocksY=Gdx.graphics.getHeight()/3f;
        allBlocksHeight =Gdx.graphics.getHeight()/2f;
        allBlocksWidth=Gdx.graphics.getWidth()*7f/8f;
        float width=allBlocksWidth/5f;
        float height= allBlocksHeight /5f;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                arrOfBlocks[i][j]=new Block(allBlocksX+i*width,allBlocksY+j*height,width,height,0,this);
                if(j==4) arrOfBlocks[i][j].value=rand.nextInt(2);
            }
        }

    }

   transient Stage stage;
   transient Skin skin;
   transient TextButton endGame;
    void createButtons(){
        stage= new Stage();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        // skin=new Skin(Gdx.files.internal("uiskin.json"));
        endGame = new TextButton("End Game", skin);
        endGame.setPosition(15,Gdx.graphics.getHeight()-45);
        endGame.setSize(90,30);
        stage.addActor(endGame);
        Gdx.input.setInputProcessor(stage);
    }

    boolean render(){
       // sh.batch.begin();

        font.draw(sh.batch,"round: "+String.valueOf(round),Gdx.graphics.getWidth()-90,Gdx.graphics.getHeight()-20);
        font.draw(sh.batch,"result: "+String.valueOf(result),Gdx.graphics.getWidth()-90,Gdx.graphics.getHeight()-40);
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(endGame.getClickListener().isPressed()){
                save(this);
            System.out.println("endGame");
            return true;
        }
        return false;
    }

    void save(WaitState ws){
        sh.add(new EndGameState(sh));
        ObjectOutputStream outputStream = null;
        try {
            FileOutputStream fileOS=new FileOutputStream("lastGame.txt");
            outputStream = new ObjectOutputStream(fileOS);
            outputStream.writeObject(ws);
            fileOS.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



    @Override
    public void update(float gameLoopTime) {
    render();

        start.set(Gdx.graphics.getWidth()/2f,10);
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                arrOfBlocks[i][j].render();
            }
        }
        sh.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,0);
        shapeRenderer.circle(Gdx.graphics.getWidth()/2f,5,15);
        if(Gdx.input.getY() < Gdx.graphics.getHeight()-35)
             shapeRenderer.line(Gdx.graphics.getWidth()/2f,0,Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
        shapeRenderer.end();
        sh.batch.begin();
           if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getY() < Gdx.graphics.getHeight()-35){
               if(render())
                   return;
               System.out.println(Gdx.input.getX());
               System.out.println(Gdx.input.getY());
                    destination.x = Gdx.input.getX();
                    destination.y = Gdx.graphics.getHeight()-Gdx.input.getY();
                    velocity=destination.sub(start).clamp(550,550);
                    round ++;
                    float i=-0.15f;
                    for(Bullet x : listOfBullets){
                        x.set(start.mulAdd(velocity,i),velocity);
                    }
                    sh.add(new PlayState(sh,this,round));
            }
    }
}

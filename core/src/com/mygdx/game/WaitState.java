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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WaitState extends State implements Serializable {
    protected WaitState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(sh.batch.getProjectionMatrix());
        createBlocks();
        createButtons();
        listOfBullets.add(new Bullet(this));
        level=sh.settings.level;
        save(this);
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
    int level;
    int floor=0;
    boolean ifCheckPoint=false;


    float allBlocksX;
    float allBlocksY;
    float allBlocksHeight;
    float allBlocksWidth;
   transient BitmapFont font=new BitmapFont();

   //continueGame odpalamy na swzystkich obiektach jak chce zaczag gre zeby potworzyc to wszystko czego nie da sie serializowac

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
       sh.settings.level=level;
   }

   //-------tworze puste bloki i losuje wartosci dla pierwszego poziomu
    void createBlocks(){
        allBlocksX=Gdx.graphics.getWidth()/16f; //50
        allBlocksY=Gdx.graphics.getHeight()/4f;
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
    transient TextButton removeLastMove;
    WaitState myThis=this;

    //--------przyciski end game i usuwanie ostatniego ruchu i ich obsluga
    void createButtons(){
        stage= sh.stage;
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        endGame = new TextButton("End", skin);
        endGame.setPosition(15,Gdx.graphics.getHeight()-45);
        endGame.setSize(45,30);
        stage.addActor(endGame);

        endGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(sh.lastElement()==myThis)
                    save(myThis);
                else {
                    if(sh.lastElement() instanceof PlayState) {
                        for (Bullet b : listOfBullets){
                            b.setStarted(false);
                        }
                    }
                }
                sh.add(new TransitionState(sh,new EndGameState(sh,result,round)));
            }

        });
        removeLastMove = new TextButton("try again", skin);
        removeLastMove.setPosition(75,Gdx.graphics.getHeight()-45);
        removeLastMove.setSize(90,30);
        stage.addActor(removeLastMove);
        Gdx.input.setInputProcessor(stage);


        removeLastMove.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y){
                for(Bullet b: listOfBullets){
                    b.setStarted(false);
                }
                System.out.println("rm");
                WaitState waitState=null;
                FileInputStream fileIS=null;
                ObjectInputStream inputStream = null;
                try {
                    File lastgame = new File("lastGame.txt");
                    if(!lastgame.exists()){
                        lastgame.createNewFile();
                    }
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
                sh.add(new TransitionState(sh,waitState));
            }
        });
    }

    //--------rysowanie tego co jest i w waitstate i w playstate

    void render(){
        sh.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.15f,0.15f,0.2f,0);
        shapeRenderer.rect(0,Gdx.graphics.getHeight()-60,Gdx.graphics.getWidth(),90);
        shapeRenderer.end();
        sh.batch.begin();

        font.draw(sh.batch,"round: "+String.valueOf(round),Gdx.graphics.getWidth()-90,Gdx.graphics.getHeight()-20);
        font.getData().setScale(2,2);
        font.draw(sh.batch,String.valueOf(result),Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()-20);
        font.getData().setScale(1,1);
        sh.batch.end();
        stage.draw();
        sh.batch.begin();

        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                arrOfBlocks[i][j].render();
            }
        }

    }


    //zapisywanie waitstate do pliku

    void save(WaitState ws){System.out.println("save");

        ObjectOutputStream outputStream = null;
        try {

            File lastgame = new File("lastGame.txt");
            if(!lastgame.exists()){
                lastgame.createNewFile();
            }
            FileOutputStream fileOS=new FileOutputStream(lastgame);
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

        start.set(Gdx.graphics.getWidth()/2f,10);
        //-----------rysowanie pilki i kreski

        sh.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(sh.settings.bulletColor);
        shapeRenderer.circle(Gdx.graphics.getWidth()/2f,5,15);
        if(Gdx.input.getY() < Gdx.graphics.getHeight()-35 && Gdx.input.getY()>60)
             shapeRenderer.line(Gdx.graphics.getWidth()/2f,0,Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
        shapeRenderer.end();
        sh.batch.begin();

        //-----rysowanie tego co w play i waitstate
        render();

        //---------nowy playstate jak klikniesz w dozwolonym miejscu
           if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getY() < Gdx.graphics.getHeight()-35 && Gdx.input.getY()>60){

                    floor=0;
                    ifCheckPoint=false;
                    destination.x = Gdx.input.getX();
                    destination.y = Gdx.graphics.getHeight()-Gdx.input.getY();
                    velocity=destination.sub(start).clamp(550,550);
                    float i=-0.15f;
                    for(Bullet x : listOfBullets){
                        x.set(start.mulAdd(velocity,i),velocity);
                        x.fast=550;
                    }
                    save(this);
                    sh.add(new PlayState(sh,this,round));
            }
    }
}

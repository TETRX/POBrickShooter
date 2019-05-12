package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;


public class PlayState extends State {
    protected PlayState(StateHandler sh) {
        super(sh);
        shapeRenderer=new ShapeRenderer();
    }
    protected PlayState(StateHandler sh, WaitState ws,int round) {
        super(sh);
        this.sh=sh;
        this.ws=ws;
        this.round=round;
        shapeRenderer=new ShapeRenderer();
        createButtons();
    }

    ShapeRenderer shapeRenderer;
    StateHandler sh;
    WaitState ws;
    int round;
    Random rand = new Random();

    Stage stage;
    Skin skin;
    TextButton faster;
    void createButtons(){
        stage= ws.stage;
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        // skin=new Skin(Gdx.files.internal("uiskin.json"));
        faster = new TextButton(">>>", skin);
        faster.setPosition(180,Gdx.graphics.getHeight()-45);
        faster.setSize(45,30);
        stage.addActor(faster);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void update(float gameLoopTime) {

        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        if(faster.getClickListener().isPressed()){
            for(Bullet b : ws.listOfBullets){
                b.faster();
            }
        }
        ws.render();
        boolean ifAdd=false;
        int bulletsInGame=0;
        Bullet b=null;
        for( Bullet a : ws.listOfBullets){
            int i=a.update(gameLoopTime);
            bulletsInGame+=i;
                    if(i==2) {
                        ifAdd = true;
                        b=a;
                        break;
                    }
        }
        if(ifAdd){
            ifAdd=false;
            ws.listOfBullets.add(new Bullet(ws,new Vector2(b.bulletPosition.x,b.bulletPosition.y),new Vector2(-b.bulletVelocity.x,b.bulletVelocity.y),true,false));
        }

        if(bulletsInGame>=10000){

            for(int i=0;i<bulletsInGame/10000;i++)
                ws.listOfBullets.add(new Bullet(ws,new Vector2(200,-10),new Vector2(0,0),true,true));
        }


        if(bulletsInGame==0){
            ws.round++;
            for(int i=0;i<5;i++){
                if(ws.arrOfBlocks[i][0].value != 0){
                    sh.remove(this);

                    ws.save(new WaitState(sh));
                    sh.add(new EndGameState(sh,ws.result,ws.round));
                }

            }


                for(int i=0;i<4;i++){
                    for(int j=0;j<5;j++) {
                        ws.arrOfBlocks[j][i].value = ws.arrOfBlocks[j][i + 1].value;
                        ws.arrOfBlocks[j][i].special = ws.arrOfBlocks[j][i + 1].special;
                    }
                }
                for(int i=0;i<5;i++){
                    if(sh.settings.level==1)
                        ws.arrOfBlocks[i][4].value=round*rand.nextInt(2)*rand.nextInt(2);
                    if(sh.settings.level==2)
                        ws.arrOfBlocks[i][4].value=round*rand.nextInt(3)*rand.nextInt(2);
                    if(sh.settings.level==3)
                        ws.arrOfBlocks[i][4].value=round*rand.nextInt(3);

                    ws.arrOfBlocks[i][4].special=0;
                }
                int i= rand.nextInt(4);
                ws.arrOfBlocks[i][4].special=1;
                ws.arrOfBlocks[i][4].value=0;
                ws.arrOfBlocks[(i+3)%5][4].value=round;
                if (round%4==2){
                     i= rand.nextInt(4);
                    ws.arrOfBlocks[i][4].special=2;
                    ws.arrOfBlocks[i][4].value=0;
                }


                int remove=0;
            for(Bullet a : ws.listOfBullets){
                a.started=false;
                if(a.canMultipy==false){
                    remove++;
                    a.canMultipy=true;
                }
            }
            for(int j=0;j<remove/2;j++){
                ws.listOfBullets.remove(0);
            }

            sh.remove(this);
        }

        int blocksInGame=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
               blocksInGame+= ws.arrOfBlocks[i][j].render();
            }
        }

        ws.render();
    }
}

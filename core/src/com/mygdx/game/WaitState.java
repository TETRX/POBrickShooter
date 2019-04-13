package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class WaitState extends State {
    protected WaitState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        createBlocks();
    }
    ShapeRenderer shapeRenderer=new ShapeRenderer();
    StateHandler sh;
    Vector2 start=new Vector2(400,10);
    Vector2 destination=new Vector2();
    Vector2 velocity;
    List<Bullet> listOfBullets=new ArrayList<Bullet>();
    Block [][] arrOfBlocks=new Block[5][5];
    int round=0;


    int allBlocksX=50;
    int allBlocksY=200;
    int allBloocksHeight=300;
    int allBlocksWidth=700;

    void createBlocks(){

        int width=allBlocksWidth/5;
        int height=allBloocksHeight/5;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                arrOfBlocks[i][j]=new Block(allBlocksX+i*width+2,allBlocksY+j*height+2,width-4,height-4,1);
            }
        }

    }


    @Override
    public void update(float gameLoopTime) {
        start.set(400,10);
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                arrOfBlocks[i][j].render();
            }
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,0);
        shapeRenderer.circle(400,5,15);
        shapeRenderer.line(400,0,Gdx.input.getX(),600-Gdx.input.getY());
        shapeRenderer.end();

            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    destination.x = Gdx.input.getX();
                    destination.y = 600-Gdx.input.getY();
                    velocity=destination.sub(start);
                    System.out.println("dest set"+start+"  "+destination);
                    round ++;
                    listOfBullets.add(new Bullet(this));
                    float i=0;
                    for(Bullet x : listOfBullets){
                        x.set(start.mulAdd(velocity,i),velocity);
                        i-=0.1;
                    }

                    sh.add(new PlayState(sh,this));

            }
    }
}

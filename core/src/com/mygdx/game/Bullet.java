package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import javafx.util.Pair;

import java.io.Serializable;

import static com.badlogic.gdx.math.MathUtils.floor;

public class Bullet implements Serializable {
    static final float radius = 0.025f*Gdx.graphics.getWidth();
    Vector2 bulletPosition;
    Vector2 bulletVelocity;
    WaitState ws;
    boolean started=false;
    boolean canMultipy=true;
    transient ShapeRenderer shapeRenderer=new ShapeRenderer();
    transient TwinkleEffect twinkleEffect;
    int fast=Gdx.graphics.getWidth();

    public Bullet(WaitState ws){
        this.ws=ws;
        twinkleEffect=new TwinkleEffect(ws.sh.effectsHandler,this);
    }

    public Bullet(WaitState ws,Vector2 start, Vector2 velocity,boolean started,boolean canMultipy){
        this(ws);
        this.started=started;
        this.canMultipy=canMultipy;
        set(start,velocity);
        twinkleEffect=new TwinkleEffect(ws.sh.effectsHandler,this);
    }

    public void set(Vector2 pos, Vector2 velocity){
        bulletPosition=new Vector2(pos.x,pos.y);
        bulletVelocity=new Vector2(velocity.x*Gdx.graphics.getHeight()/800, velocity.y*Gdx.graphics.getWidth()/600);
    }

    void continueGame(){
        twinkleEffect=new TwinkleEffect(ws.sh.effectsHandler,this);
        shapeRenderer=new ShapeRenderer();
    }

    public void faster(){if(fast<Gdx.graphics.getWidth()*10/3) fast+=500; bulletVelocity.clamp(fast,fast);System.out.println(fast);}

    public Pair<Integer,Integer> update(float gameLoopTime) {
        if(Math.abs(bulletVelocity.y)<2)
            bulletVelocity.y*=2;

        Vector2 oldPosition=new Vector2(bulletPosition);
        bulletPosition.mulAdd(bulletVelocity,gameLoopTime);

        if(bulletPosition.y<radius/2 && started==true)
            return new Pair<Integer,Integer>(0,0);

        //-----------------odbijanie od brzegow
        if(ws.floor>0 && bulletPosition.y<2*radius && bulletVelocity.y<0){
            bulletVelocity.y*=(-1);
            ws.floor--;
        }

        if(bulletPosition.y>radius/2) {
            started = true;
        }
        if(bulletPosition.y<0)
            return new Pair<Integer,Integer>(0,1);
        if((bulletPosition.x-radius<0 && bulletVelocity.x<0) || (bulletPosition.x> Gdx.graphics.getWidth()-radius && bulletVelocity.x>0))
            bulletVelocity.x=-bulletVelocity.x;
        if(bulletPosition.y>=Gdx.graphics.getHeight()-radius-60 && bulletVelocity.y>0)
            bulletVelocity.y=-bulletVelocity.y;


        float width=ws.allBlocksWidth/5;
        float height=ws.allBlocksHeight/5;

        //----------zdobywanie specjalnych

        int i=floor((bulletPosition.x-ws.allBlocksX)/width);
        int j=floor((bulletPosition.y-ws.allBlocksY)/height);
        if(i>=0 && i<5 && j>=0 && j<5 && ws.arrOfBlocks[i][j].special== 1){
            if(Math.abs(bulletPosition.x-(ws.arrOfBlocks[i][j].x+ws.arrOfBlocks[i][j].height/2))<2*radius && Math.abs(bulletPosition.y-(ws.arrOfBlocks[i][j].y+ws.arrOfBlocks[i][j].width/2))<2*radius){
                ws.arrOfBlocks[i][j].special=0;
                return new Pair<Integer,Integer>(1,1);
            }
        }

        if(i>=0 && i<5 && j>=0 && j<5 && ws.arrOfBlocks[i][j].special== 2 && canMultipy){
            if(Math.abs(bulletPosition.x-(ws.arrOfBlocks[i][j].x+ws.arrOfBlocks[i][j].height/2))<2*radius && Math.abs(bulletPosition.y-(ws.arrOfBlocks[i][j].y+ws.arrOfBlocks[i][j].width/2))<2*radius){
              canMultipy=false;
               return new Pair<Integer,Integer>(2,1);

            }
        }

        if(i>=0 && i<5 && j>=0 && j<5 && ws.arrOfBlocks[i][j].special== 3){
            if(Math.abs(bulletPosition.x-(ws.arrOfBlocks[i][j].x+ws.arrOfBlocks[i][j].height/2))<2*radius-5 && Math.abs(bulletPosition.y-(ws.arrOfBlocks[i][j].y+ws.arrOfBlocks[i][j].width/2))<2*radius-5){
                ws.arrOfBlocks[i][j].special=0;
                ws.arrOfBlocks[i][j].special=0;
                return new Pair<Integer,Integer>(3,1);
            }
        }

        //----------ODBIJANIE OD KLOCKOW

                     i=floor((bulletPosition.x+radius-ws.allBlocksX)/width);
                     j=floor((bulletPosition.y+radius-ws.allBlocksY)/height);
                    if(i>=0 && i<5 && j>=0 && j<5 && ws.arrOfBlocks[i][j].value > 0) {
                        ws.arrOfBlocks[i][j].decrease();
                        ws.arrOfBlocks[i][j].fontSize=2.1f;
                        if ( oldPosition.x + radius  -1< ws.arrOfBlocks[i][j].x ){
                            bulletVelocity.x = -bulletVelocity.x;
                        }

                         if ( oldPosition.y + radius -1 < ws.arrOfBlocks[i][j].y ){
                            bulletVelocity.y = -bulletVelocity.y;

                        }

                    }
                    else {

                        i = floor((bulletPosition.x - radius - ws.allBlocksX) / width);
                        j = floor((bulletPosition.y - radius - ws.allBlocksY) / height);
                        if (i >= 0 && i < 5 && j >= 0 && j < 5 && ws.arrOfBlocks[i][j].value > 0) {
                            ws.arrOfBlocks[i][j].decrease();
                            ws.arrOfBlocks[i][j].fontSize=2.1f;
                            if (oldPosition.x - radius +1 > ws.arrOfBlocks[i][j].x + width) {
                                bulletVelocity.x = -bulletVelocity.x;
                            }
                            if (oldPosition.y - radius +1 > ws.arrOfBlocks[i][j].y + height) {
                                bulletVelocity.y = -bulletVelocity.y;
                            }

                        }


                        else {

                            i = floor((bulletPosition.x + radius - ws.allBlocksX) / width);
                            j = floor((bulletPosition.y - radius - ws.allBlocksY) / height);
                            if(i>=0 && i<5 && j>=0 && j<5 && ws.arrOfBlocks[i][j].value > 0) {
                                ws.arrOfBlocks[i][j].decrease();
                                ws.arrOfBlocks[i][j].fontSize=2.1f;
                                if ( oldPosition.x + radius  -1< ws.arrOfBlocks[i][j].x ){
                                    bulletVelocity.x = -bulletVelocity.x;
                                }
                                if( oldPosition.y - radius +1> ws.arrOfBlocks[i][j].y + height){
                                    bulletVelocity.y = -bulletVelocity.y;
                                }

                            }

                            else {

                                i = floor((bulletPosition.x - radius - ws.allBlocksX) / width);
                                j = floor((bulletPosition.y + radius - ws.allBlocksY) / height);
                                if(i>=0 && i<5 && j>=0 && j<5 && ws.arrOfBlocks[i][j].value > 0) {
                                    ws.arrOfBlocks[i][j].decrease();
                                    ws.arrOfBlocks[i][j].fontSize=2.1f;
                                    if ( oldPosition.x - radius  +1> ws.arrOfBlocks[i][j].x+width){
                                        bulletVelocity.x = -bulletVelocity.x;
                                    }
                                    if ( oldPosition.y + radius  -1< ws.arrOfBlocks[i][j].y ){
                                        bulletVelocity.y = -bulletVelocity.y;
                                    }

                                }
                            }
                        }
                    }
                    render();

        return new Pair<Integer,Integer>(0,1);
        }

        public void render(){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(ws.sh.settings.bulletColor);
            shapeRenderer.circle(bulletPosition.x,bulletPosition.y,radius);
            shapeRenderer.end();
        }

        void setStarted(boolean a){started=a;}

}

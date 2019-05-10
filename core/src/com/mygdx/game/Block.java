package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.io.Serializable;

public class Block implements Serializable {
    float x,y,height,width;
    int value;
    WaitState ws;
    int special=0; //1-new bullet
    Block (float x,float y, float height, float width, int value, WaitState ws){
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
        this.value=value;
        this.ws=ws;
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(ws.sh.batch.getProjectionMatrix());
        font=ws.font;
    }

  transient  ShapeRenderer shapeRenderer;
   transient BitmapFont font;
   void continueGame(){
       font=ws.font;
       shapeRenderer=new ShapeRenderer();
   }


    public int render(){
        if(value>0 && special==0){
            ws.sh.batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(ws.sh.settings.brickColor);
            shapeRenderer.rect(x+2,y+2,height-4,width-4);
            shapeRenderer.end();
            ws.sh.batch.begin();
            font.draw(ws.sh.batch,String.valueOf(value),x+height/2,y+width/2);
            return 1;
        }
        if(special==1){
            ws.sh.batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(ws.sh.settings.bulletColor);
            shapeRenderer.circle(x+height/2,y+width/2,15);
            shapeRenderer.end();
            ws.sh.batch.begin();

        }
        if(special==2){
            ws.sh.batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(ws.sh.settings.bulletColor);
            shapeRenderer.circle(x+height/2,y+width/2,15);
            shapeRenderer.end();
            ws.sh.batch.begin();
        }
        return 0;
    }

    public void decrease(){
        value--;
        ws.result++;
        if(value==0){
            ws.sh.effectsHandler.tempor.add(new ParticleExplosionEffect(ws.sh.effectsHandler,new Vector2(x, Gdx.graphics.getHeight()-y)));
        }
    }
}

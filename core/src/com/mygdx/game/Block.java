package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class Block {
    float x,y,height,width;
    int value;
    WaitState ws;
    Block (float x,float y, float height, float width, int value, WaitState ws){
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
        this.value=value;
        this.ws=ws;
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(ws.sh.batch.getProjectionMatrix());
    }
    ShapeRenderer shapeRenderer;
    BitmapFont font=new BitmapFont();


    public int render(){
        if(value>0){
            ws.sh.batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1,0,0,0);
            shapeRenderer.rect(x,y,height,width);
            shapeRenderer.end();
            ws.sh.batch.begin();
            font.draw(ws.sh.batch,String.valueOf(value),x+height/2,y+width/2);
            return 1;
        }
        return 0;
    }

    public void decrease(){
        value--;ws.result++;
    }
}

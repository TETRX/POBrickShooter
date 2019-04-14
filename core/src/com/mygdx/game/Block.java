package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class Block {
    float x,y,height,width;
    Color color;
    int value;
    Block (float x,float y, float height, float width, int value){
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
        this.value=value;
    }
    ShapeRenderer shapeRenderer=new ShapeRenderer();
    Batch batch=new SpriteBatch();
    BitmapFont font=new BitmapFont();


    public int render(){
        if(value>0){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1,0,0,0);
            shapeRenderer.rect(x,y,height,width);
            shapeRenderer.end();
            batch.begin();
            font.draw(batch,String.valueOf(value),x+height/2,y+width/2);
            batch.end();

            return 1;
        }
        return 0;
    }

    public void decrease(){
        value--;
    }
}

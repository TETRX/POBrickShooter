package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class PlayState extends State {
    protected PlayState(StateHandler sh) {
        super(sh);
        shapeRenderer=new ShapeRenderer();
    }
    protected PlayState(StateHandler sh, Vector2 start, Vector2 destination, WaitState ws) {
        super(sh);
        this.sh=sh;
        this.ws=ws;
        shapeRenderer=new ShapeRenderer();
        bulletPosition=new Vector2();
        bulletPosition.x=start.x;
        bulletPosition.y=start.y;
        bulletVelocity=destination.sub(start);
        bulletVelocity.clamp(550,550);
    }
    static final float radius = 15F;
    Vector2 bulletPosition;
    Vector2 bulletVelocity;
    ShapeRenderer shapeRenderer;
    StateHandler sh;
    WaitState ws;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();


    @Override
    public void update(float gameLoopTime) {
        bulletPosition.mulAdd(bulletVelocity,gameLoopTime);
        if(bulletPosition.x<=0 || bulletPosition.x>=800)
            bulletVelocity.x=-bulletVelocity.x;
        if(bulletPosition.y>=600)
            bulletVelocity.y=-bulletVelocity.y;
        if(bulletPosition.y<=0)
            sh.remove(this);
        if(bulletPosition.y>400 && bulletPosition.y<450 && (int)(bulletPosition.x)/120<5 && ws.blocks[(int)(bulletPosition.x)/120]>0){
            ws.blocks[(int)(bulletPosition.x)/120]--;
            bulletVelocity.y=-bulletVelocity.y;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(222,1,1,0);
        batch.begin();
        for(int i=0;i<5;i++){
            if(ws.blocks[i]>0){
                shapeRenderer.rect(120*i,400,100,50);
                font.draw(batch,String.valueOf(ws.blocks[i]),i*120,400);
            }
        }
        batch.end();
        shapeRenderer.circle(bulletPosition.x,bulletPosition.y,radius);
        shapeRenderer.end();
    }
}

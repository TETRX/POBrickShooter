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
    protected PlayState(StateHandler sh, WaitState ws) {
        super(sh);
        this.sh=sh;
        this.ws=ws;
        shapeRenderer=new ShapeRenderer();

    }

    ShapeRenderer shapeRenderer;
    StateHandler sh;
    WaitState ws;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();


    @Override
    public void update(float gameLoopTime) {
        int bulletsInGame=0;
        for(Bullet b : ws.listOfBullets){
            bulletsInGame+=b.update(gameLoopTime);
        }
        if(bulletsInGame==0)
            sh.remove(this);
        int blocksInGame=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
               blocksInGame+= ws.arrOfBlocks[i][j].render();
            }
        }
        if(blocksInGame==0)
            sh.add(new EndGameState(sh));

    }
}

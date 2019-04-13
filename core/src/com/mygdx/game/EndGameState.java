package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EndGameState extends State {

    protected EndGameState(StateHandler sh) {
        super(sh);
    }
    Batch batch=new SpriteBatch();
    BitmapFont font=new BitmapFont();

    @Override
    public void update(float gameLoopTime) {

        batch.begin();
        font.draw(batch,"GAME OVER",200,400);
        batch.end();
    }
}

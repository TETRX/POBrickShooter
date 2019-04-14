package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EndGameState extends State {

    protected EndGameState(StateHandler sh) {
        super(sh);
    }
    BitmapFont font=new BitmapFont();

    @Override
    public void update(float gameLoopTime) {
        font.draw(sh.batch,"GAME OVER",200,400);
    }
}

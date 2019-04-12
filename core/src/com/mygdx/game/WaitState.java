package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class WaitState extends State {
    protected WaitState(StateHandler sh) {
        super(sh);
    }
    Vector2 start;
    Vector2 destination;
    @Override
    public void update(float gameLoopTime) {
        System.out.println("here");
        while (true){
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                start.x=Gdx.input.getX();
                start.y=Gdx.input.getY();
                break;
            }
        }
        System.out.println("Start point set");
        while (true){
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                destination.x = Gdx.input.getX();
                destination.y = Gdx.input.getY();
                break;
            }
        }
        System.out.println("dest set");
        sh.set(new PlayState(sh,start,destination));
    }
}

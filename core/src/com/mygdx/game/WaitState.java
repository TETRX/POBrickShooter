package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Thread.sleep;

public class WaitState extends State {
    protected WaitState(StateHandler sh) {
        super(sh);
        this.sh=sh;
    }
    ShapeRenderer shapeRenderer=new ShapeRenderer();
    StateHandler sh;
    Vector2 start=new Vector2(400,0);
    Vector2 destination=new Vector2();

    int blocks[]={5,5,5,5,5};

    @Override
    public void update(float gameLoopTime) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.circle(400,10,15F);
        for(int i=0;i<5;i++){
            if(blocks[i]>0){
                shapeRenderer.rect(120*i,400,100,50);
            }
        }
        shapeRenderer.line(400,0,Gdx.input.getX(),600-Gdx.input.getY());
        shapeRenderer.end();

            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    destination.x = Gdx.input.getX();
                    destination.y = 600-Gdx.input.getY();

                    System.out.println("dest set"+start+"  "+destination);
                    sh.add(new PlayState(sh,start,destination,this));

            }
    }
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Vector;

// a class that manages States (specifically the one on top of the vector)

public class StateHandler extends Vector<State> {

    Batch batch;
    Stage stage;

    StateHandler(Batch batch){
        this.batch=batch;
        stage=new Stage();
    }

    public void update(float gameLoopTime){
        lastElement().update(gameLoopTime);
        if(size()>10){
            remove(0);
        }
       // System.out.println(size());
    }

    public void set(State state) {
        set(size()-1,state);
    }


}

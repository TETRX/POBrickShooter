package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import javafx.scene.shape.Circle;

public class PlayState extends State {
    protected PlayState(StateHandler sh) {
        super(sh);
        shapeRenderer=new ShapeRenderer();
    }
    protected PlayState(StateHandler sh, Vector2 start, Vector2 destination) {
        super(sh);
        shapeRenderer=new ShapeRenderer();
        bulletPosition=start;
        bulletVelocity=destination.sub(start);
    }
    static final float radius = 15F;
    Vector2 bulletPosition;
    Vector2 bulletVelocity;
    ShapeRenderer shapeRenderer;

    @Override
    public void update(float gameLoopTime) {
        bulletPosition.mulAdd(bulletVelocity,gameLoopTime);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.circle(bulletPosition.x,bulletPosition.y,radius);
        shapeRenderer.end();
    }
}

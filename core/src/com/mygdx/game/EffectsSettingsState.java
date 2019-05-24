package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class EffectsSettingsState extends WaitState{
    Bullet pres;
    Block presBlock;
    EffectsSettingsState(StateHandler stateHandler){
        super(stateHandler);
        pres=new Bullet(this, new Vector2((float)Gdx.graphics.getWidth()/3,(float)Gdx.graphics.getHeight()/10), Vector2.Zero,true,false);
        pres.shapeRenderer=new ShapeRenderer();
        float allBlocksHeight =Gdx.graphics.getHeight()/2f;
        float allBlocksWidth=Gdx.graphics.getWidth()*7f/8f;
        float width=allBlocksWidth/5f;
        float height= allBlocksHeight /5f;
        presBlock = new Block( (float)Gdx.graphics.getWidth()*2/3,(float)Gdx.graphics.getHeight()/20,height,width,1,this);
    }

    @Override
    public void update(float gameLoopTime) {
        sh.batch.end();
        pres.render();
        sh.batch.begin();
        presBlock.render();
    }
}

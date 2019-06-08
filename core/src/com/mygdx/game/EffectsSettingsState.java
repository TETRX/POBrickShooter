package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class EffectsSettingsState extends WaitState{
    Bullet pres;
    Block presBlock;
    final float height;
    final float width;


    protected void setSlider(Slider slider, int i){
        slider.setPosition((float) Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()*(float)(i+2)/14);
        slider.setSize((float) Gdx.graphics.getWidth()*2/3, (float) Gdx.graphics.getHeight()/24);
        stage.addActor(slider);
    }

    EffectsSettingsState(StateHandler stateHandler){
        super(stateHandler);
        stage.clear();
        if(pres==null)
        pres=new Bullet(this, new Vector2((float)Gdx.graphics.getWidth()/3,(float)Gdx.graphics.getHeight()/10), Vector2.Zero,true,false);
        pres.shapeRenderer=new ShapeRenderer();
        float allBlocksHeight =Gdx.graphics.getHeight()/2f;
        float allBlocksWidth=Gdx.graphics.getWidth()*7f/8f;
        width=allBlocksWidth/5f;
        height= allBlocksHeight /5f;
        presBlock = new Block( (float)Gdx.graphics.getWidth()*2/3,(float)Gdx.graphics.getHeight()/20,height,width,1,this);
    }

    @Override
    public void update(float gameLoopTime) {
        sh.batch.end();
        pres.render();
        sh.batch.begin();
        presBlock.render();
    }

    @Override
    void save(WaitState ws){ }
}

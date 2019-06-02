package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class TrailEffectsSettingState extends EffectsSettingsState {

    Button toMenu;
    Button apply;
    Button discard;
    SelectBox<String> selectBox;
    Settings save;
    Slider particleSize;

    TrailEffectsSettingState(StateHandler stateHandler) {
        super(stateHandler);
        try {
            save= (Settings) stateHandler.settings.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        selectBox=new SelectBox<String>(skin);
        selectBox.setItems("Plain Color", "Random Color", "Rainbow Color");
        selectBox.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*4f/6f);
        selectBox.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        selectBox.setDisabled(false);
        if (sh.settings.trailEffectColor instanceof RandomColor){
            selectBox.setSelected("Random Color");
        }
        else if (sh.settings.trailEffectColor instanceof Rainbow){
            selectBox.setSelected("Rainbow Color");
        }
        else {
            selectBox.setSelected("Plain Color");
        }

        stage.addActor(selectBox);

        toMenu= new TextButton("menu", skin);
        toMenu.setPosition( Gdx.graphics.getWidth()*17/20,Gdx.graphics.getHeight()/40);
        toMenu.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(toMenu);

        apply= new TextButton("Apply", skin);
        apply.setPosition(Gdx.graphics.getWidth()/2F-Gdx.graphics.getWidth()*3F/16F,Gdx.graphics.getHeight()*3f/6f);
        apply.setSize(Gdx.graphics.getWidth()*3F/8F,Gdx.graphics.getHeight()/8f);
        stage.addActor(apply);

        discard= new TextButton("Discard Changes", skin);
        discard.setPosition( Gdx.graphics.getWidth()*3/20,Gdx.graphics.getHeight()*35/40);
        discard.setSize((float)Gdx.graphics.getWidth()*3/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(discard);

        particleSize=new Slider(1,10,1f,false,skin);
        particleSize.setValue(stateHandler.settings.trailParticleSize);
        setSlider(particleSize,3);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float gameLoopTime) {
        super.update(gameLoopTime);
        font.draw(sh.batch,"Particle Size: ", Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()*11.5f/28);
        font.draw(sh.batch, String.valueOf((int)particleSize.getValue()),Gdx.graphics.getWidth()*5/6,Gdx.graphics.getHeight()*11.5f/28);
        sh.batch.end();
        stage.draw();
        selectBox.act(gameLoopTime);
        selectBox.getList().act(gameLoopTime);
        selectBox.getScrollPane().act(gameLoopTime);
        sh.batch.begin();
        if(apply.isPressed()){
            Effect.pixmap.setColor(Color.CLEAR);
            Effect.pixmap.fill();
            pres.setStarted(false);
            sh.settings.trailParticleSize=(int)particleSize.getValue();
            if(selectBox.getSelected()=="Plain Color"){
                sh.settings.trailEffectColor=new StandardColor(Color.RED);
            }
            else if(selectBox.getSelected()=="Rainbow Color"){
                sh.settings.trailEffectColor=Settings.r;
            }
            else if(selectBox.getSelected()=="Random Color"){
                sh.settings.trailEffectColor=new RandomColor();
            }
            pres.setStarted(true);
        }
        if (discard.isPressed()){
            Effect.pixmap.setColor(Color.CLEAR);
            Effect.pixmap.fill();
            pres.setStarted(false);
            sh.settings=save;
            sh.remove(this);
            sh.add(new TransitionState(sh,new MenuState(sh)));
        }
        if(toMenu.isPressed()){
            Effect.pixmap.setColor(Color.CLEAR);
            Effect.pixmap.fill();
            pres.setStarted(false);
            sh.remove(this);
            sh.add(new TransitionState(sh,new MenuState(sh)));
        }
    }
}

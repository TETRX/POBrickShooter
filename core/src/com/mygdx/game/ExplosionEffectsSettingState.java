package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Select;
import com.badlogic.gdx.utils.Timer;

public class ExplosionEffectsSettingState extends EffectsSettingsState {

    private boolean exploding=false;
    Button toMenu;
    Button apply;
    Button discard;
    Button test;
    SelectBox<String> selectBox;
    Settings save;
    Slider particleSize;

    ExplosionEffectsSettingState(StateHandler stateHandler) {
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
        if (sh.settings.explosionEffectColor instanceof RandomColor){
            selectBox.setSelected("Random Color");
        }
        else if (sh.settings.explosionEffectColor instanceof Rainbow){
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

        test= new TextButton("Test", skin);
        test.setPosition( Gdx.graphics.getWidth()*17/20,Gdx.graphics.getHeight()*35/40);
        test.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(test);

        particleSize=new Slider(1,10,1f,false,skin);
        particleSize.setValue(stateHandler.settings.explosionParticleSize);
        setSlider(particleSize,3);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float gameLoopTime) {
        super.update(gameLoopTime);
        GlyphLayout g = new GlyphLayout();
        g.setText(font, "Particle Size:");
        font.draw(sh.batch,"Particle Size: ", Gdx.graphics.getWidth()/6f ,Gdx.graphics.getHeight()*11.5f/28+ g.height);
        font.draw(sh.batch, String.valueOf((int)particleSize.getValue()),Gdx.graphics.getWidth()*5f/6f,Gdx.graphics.getHeight()*11.5f/28);
        sh.batch.end();
        stage.draw();
        selectBox.act(gameLoopTime);
        selectBox.getList().act(gameLoopTime);
        selectBox.getScrollPane().act(gameLoopTime);
        sh.batch.begin();
        if(test.isPressed() && !exploding){
            presBlock.decrease();
            exploding=true;
            new Timer().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    exploding=false;
                    presBlock=new Block( (float)Gdx.graphics.getWidth()*2/3,(float)Gdx.graphics.getHeight()/20,height,width,1,ExplosionEffectsSettingState.this);
                }
            },1f);
        }
        if(apply.isPressed()){
            sh.settings.explosionParticleSize=(int)particleSize.getValue();
            if(selectBox.getSelected()=="Plain Color"){
                sh.settings.explosionEffectColor=new StandardColor(Color.RED);
            }
            else if(selectBox.getSelected()=="Rainbow Color"){
                sh.settings.explosionEffectColor=Settings.r;
            }
            else if(selectBox.getSelected()=="Random Color"){
                sh.settings.explosionEffectColor=new RandomColor();
            }
        }
        if (discard.isPressed()){
            pres.setStarted(false);
            Effect.pixmap.setColor(Color.CLEAR);
            Effect.pixmap.fill();
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

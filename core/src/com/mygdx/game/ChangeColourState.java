package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class ChangeColourState extends State{
    Stage stage;
    Button apply;
    Button cancel;
    Skin skin;
    Settings beingSet;
    BitmapFont font;

    //Sliders
    Slider []back=new Slider[4];
    Slider []bullet=new Slider[4];
    Slider []brick=new Slider[4];

    private void setSlider(Slider slider,int i){
        slider.setPosition((float) Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()*(float)(i+2)/14);
        slider.setSize((float) Gdx.graphics.getWidth()*2/3, (float) Gdx.graphics.getHeight()/24);
        stage.addActor(slider);
    }

    private void setButton(Button button,  float x, float y){

        button.setPosition(x,y);
        button.setSize((float)Gdx.graphics.getWidth()/10,(float) Gdx.graphics.getHeight()/10);
        stage.addActor(button);
    }

    private void updateSettings(){
        beingSet.bulletColor.set(bullet[0].getValue(),bullet[1].getValue(),bullet[2].getValue(),bullet[3].getValue());
        beingSet.backgroundColor.set(back[0].getValue(),back[1].getValue(),back[2].getValue(),back[3].getValue());
        beingSet.brickColor.set(brick[0].getValue(),brick[1].getValue(),brick[2].getValue(),brick[3].getValue());
    }

    protected ChangeColourState(StateHandler sh) {
        super(sh);
        this.sh=sh;
        this.beingSet=new Settings();
        skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
        font=new BitmapFont();
        font.draw(sh.batch,"Background Red", (float) Gdx.graphics.getWidth()/18,(float)Gdx.graphics.getHeight()/14);
        stage= new Stage();
        for(int i=0;i<4;i++){
            back[i]=new Slider(0,1, (float) 0.01,false,skin);
            setSlider(back[i],3-i);
        }
        for(int i=0;i<4;i++){
            bullet[i]=new Slider(0,1, (float) 0.01,false,skin);
            setSlider(bullet[i],(3-i)+4);
        }
        for(int i=0;i<4;i++){
            brick[i]=new Slider(0,1, (float) 0.01,false,skin);
            setSlider(brick[i],3-i+8);
        }
        back[0].setValue(sh.settings.backgroundColor.r);
        back[1].setValue(sh.settings.backgroundColor.g);
        back[2].setValue(sh.settings.backgroundColor.b);
        back[3].setValue(sh.settings.backgroundColor.a);

        brick[0].setValue(sh.settings.brickColor.r);
        brick[1].setValue(sh.settings.brickColor.g);
        brick[2].setValue(sh.settings.brickColor.b);
        brick[3].setValue(sh.settings.brickColor.a);

        bullet[0].setValue(sh.settings.bulletColor.r);
        bullet[1].setValue(sh.settings.bulletColor.g);
        bullet[2].setValue(sh.settings.bulletColor.b);
        bullet[3].setValue(sh.settings.bulletColor.a);



        cancel=new TextButton("Cancel",skin);
        setButton(cancel,(float) Gdx.graphics.getWidth()/20,(float)Gdx.graphics.getHeight()/40);
        apply=new TextButton("Apply",skin);
        setButton(apply,(float) Gdx.graphics.getWidth()*17/20,(float)Gdx.graphics.getHeight()/40);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void update(float gameLoopTime) {
        sh.batch.end();
        stage.draw();
        sh.batch.begin();
        font.draw(sh.batch,"Background Red", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*27/28);
        font.draw(sh.batch,"Background Green", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*25/28);
        font.draw(sh.batch,"Background Blue", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*23/28);
        font.draw(sh.batch,"Background Alpha", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*21/28);
        font.draw(sh.batch,"Brick Red", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*19/28);
        font.draw(sh.batch,"Brick Green", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*17/28);
        font.draw(sh.batch,"Brick Blue", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*15/28);
        font.draw(sh.batch,"Brick Alpha", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*13/28);
        font.draw(sh.batch,"Bullet Red", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*11/28);
        font.draw(sh.batch,"Bullet Green", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*9/28);
        font.draw(sh.batch,"Bullet Blue", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*7/28);
        font.draw(sh.batch,"Bullet Alpha", (float) Gdx.graphics.getWidth()/50,(float)Gdx.graphics.getHeight()*5/28);

        if(cancel.getClickListener().isPressed()){
            sh.add(new TransitionState(sh, new MenuState(sh)));
        }
        else if(apply.getClickListener().isPressed()){
            updateSettings();
            sh.settings.set(this.beingSet);
            sh.remove(this);
            sh.add(new TransitionState(sh,new MenuState(sh)));
        }
    }
}

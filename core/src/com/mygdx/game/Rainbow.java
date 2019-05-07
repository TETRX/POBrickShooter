package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

public class Rainbow extends Thread{
    public Color color;
    float pr,pg,pb;
    Rainbow(){
        pr=0.03f;
        pb=0.05f;
        pg=0.02f;
        color=new Color(1,1,1,1);
    }
    void setColor(){
        color.set((float) (Math.cos(TimeUtils.millis()/Math.PI*pr)+1)/2,(float) (Math.cos(TimeUtils.millis()/Math.PI*pb)+1)/2,(float)(Math.cos(TimeUtils.millis()/Math.PI*pg)+1)/2,1f);
    }           // Math.cos((...)*p)
    @Override
    public void run() {
        while (true){
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setColor();
        }
    }
}

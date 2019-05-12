package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class Settings {
    public static Rainbow r = new Rainbow();
    static {
        r.start();
    }
    //Bullet
    int level=2;
    public static final Color defaultBulletColor = Color.WHITE;
    Color bulletColor;
    //Brick
    public static final Color defaultBrickColor = Color.RED;
    Color brickColor;
    //Background
    public static final Color defaultBackgroundColor = Color.BLACK;
    Color backgroundColor;

    public Settings(Color bulletColor, Color brickColor, Color backgroundColor){
        bulletColor = new Color();
        this.bulletColor.set(bulletColor);
        brickColor = new Color();
        this.brickColor.set(brickColor);
        backgroundColor=new Color();
        this.backgroundColor.set(backgroundColor);
    }
    public Settings(){
        bulletColor = new Color();
        this.bulletColor=defaultBulletColor;
        brickColor = new Color();
        this.brickColor.set(defaultBrickColor);
        backgroundColor=new Color();
        this.backgroundColor.set(defaultBackgroundColor);
    }
    public void set(Settings x){
        bulletColor.set(x.bulletColor);
        brickColor.set(x.brickColor);
        backgroundColor.set(x.backgroundColor);
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class BackgroundEffect extends Effect {
    BackgroundEffect(EffectsHandler effectsHandler) {
        super(effectsHandler);
    }

    @Override
    public boolean calculate(float deltaTime) {
        for(int i=0;i< Gdx.graphics.getHeight();i++){
            for (int j=0;j<Gdx.graphics.getWidth();j++){
                if(pixmap.getPixel(i,j)==0){
                    pixmap.setColor(effectsHandler.stateHandler.settings.backgroundColor);
                    pixmap.drawPixel(i,j);
                }
            }
        }
        return true;
    }
}

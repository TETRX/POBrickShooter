package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture effects;
	EffectsHandler effectsHandler;
	private StateHandler stateHandler;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		effectsHandler = new EffectsHandler();
		stateHandler = new StateHandler(batch,effectsHandler);
		effectsHandler.setUp(stateHandler);
		stateHandler.add(new MenuState(stateHandler));
		effects=new Texture(Effect.pixmap);
		effectsHandler.start();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(stateHandler.settings.backgroundColor.r, stateHandler.settings.backgroundColor.g, stateHandler.settings.backgroundColor.b, stateHandler.settings.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		effects.dispose();
		effects=new Texture(Effect.pixmap);
		batch.begin();
		batch.draw(effects,0,0);
		stateHandler.update(Gdx.graphics.getDeltaTime());
		batch.end();

	}
	
	@Override
	public void dispose () {
		//EffectsHandler.stop();
		Effect.pixmap.dispose();
		batch.dispose();
		//img.dispose();
		effects.dispose();
	}
}

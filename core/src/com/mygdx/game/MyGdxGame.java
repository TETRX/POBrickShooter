package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	private StateHandler stateHandler;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stateHandler = new StateHandler(batch);
		stateHandler.add(new MenuState(stateHandler));
		//stateHandler.add(new WaitState(stateHandler));
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(stateHandler.settings.backgroundColor.r, stateHandler.settings.backgroundColor.g, stateHandler.settings.backgroundColor.b, stateHandler.settings.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		///batch.draw(img, 0, 0);;
		batch.begin();
		stateHandler.update(Gdx.graphics.getDeltaTime());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}

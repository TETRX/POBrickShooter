package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture effects;
	EffectsHandler effectsHandler;
	Stage stage;
	Skin skin;
	SelectBox<ScreenSize> selectBox;
	BitmapFont bitmapFont;
	private StateHandler stateHandler;
	boolean a;

	class ScreenSize{
		public Integer x,y;

		ScreenSize(int x, int y){
			this.x=x;
			this.y=y;
		}

		@Override
		public String toString() {
			if(x>0)
				return x.toString() + "x" + y.toString();
			else return "----";
		}
	}

	ScreenSize b = new ScreenSize(0,0);


	@Override
	public void create () {
		if(!a){
			batch = new SpriteBatch();
			stage= new Stage();
			skin=new Skin(Gdx.files.internal("ccskin/clean-crispy-ui.json"));
			selectBox= new SelectBox<ScreenSize>(skin);
			stage.addActor(selectBox);
			selectBox.setWidth(Gdx.graphics.getWidth()/2);
			selectBox.setHeight(Gdx.graphics.getHeight()/4);
			selectBox.setX(Gdx.graphics.getWidth()/4);
			selectBox.setY(Gdx.graphics.getHeight()/3);
			selectBox.setItems(new ScreenSize(300,400),new ScreenSize(450,600),new ScreenSize(600,800),new ScreenSize(900,1200), b);
			selectBox.setSelected(b);
			selectBox.setDisabled(false);
			Gdx.input.setInputProcessor(stage);
			bitmapFont=new BitmapFont();
			System.out.println("Aaa");
		}
		else {
			batch.dispose();
			Gdx.graphics.setWindowedMode(b.x,b.y);
			batch=new SpriteBatch();
			effectsHandler = new EffectsHandler();
			stateHandler = new StateHandler(batch, effectsHandler);
			effectsHandler.setUp(stateHandler);
			stateHandler.add(new MenuState(stateHandler));
			effects = new Texture(Effect.pixmap);
			effectsHandler.start();
		}
	}

	@Override
	public void render () {
		if(!a){
			batch.begin();
			bitmapFont.draw(batch,"Select Window Size:",Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()*2/3);
			batch.end();
			stage.draw();
			stage.act();
			if(selectBox.getSelected()!=b){
				b=selectBox.getSelected();
				a=true;
				create();
			}
			/*selectBox.act(Gdx.graphics.getDeltaTime());*/
			//a=true;
			//create();
		}
		else {
			Gdx.gl.glClearColor(stateHandler.settings.backgroundColor.r, stateHandler.settings.backgroundColor.g, stateHandler.settings.backgroundColor.b, stateHandler.settings.backgroundColor.a);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			effects.dispose();
			effects = new Texture(Effect.pixmap);
			batch.begin();
			batch.draw(effects, 0, 0);
			stateHandler.update(Gdx.graphics.getDeltaTime());
			batch.end();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		if(a) {
			//EffectsHandler.stop();
			Effect.pixmap.dispose();
			//img.dispose();
			effects.dispose();
		}
	}
}

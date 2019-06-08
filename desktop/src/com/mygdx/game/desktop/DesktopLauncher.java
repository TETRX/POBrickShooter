package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;


public class DesktopLauncher {
	static LwjglApplicationConfiguration a;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		a=config;
		config.title="BrickShooter";
		config.width=300;
		config.height=300;
		config.resizable=false;
		config.samples=4;
		config.forceExit=true;
		/*config.fullscreen=true;*/
		MyGdxGame game=new MyGdxGame();
		new LwjglApplication(game, config);
	}
}

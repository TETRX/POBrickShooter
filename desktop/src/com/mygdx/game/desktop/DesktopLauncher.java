package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import org.lwjgl.opengl.GL11;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="BrickShooter";
		config.width=600;
		config.height=800;
		config.resizable=false;
		config.samples=4;
		MyGdxGame game=new MyGdxGame();
		new LwjglApplication(game, config);
	}
}

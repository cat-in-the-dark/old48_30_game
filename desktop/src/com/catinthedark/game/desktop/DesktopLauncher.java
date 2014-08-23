package com.catinthedark.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.catinthedark.game.LDGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 640;
		config.width = 1024;
		config.title = "Beneath the surface of the democracy";
		new LwjglApplication(new LDGame(), config);
	}
}

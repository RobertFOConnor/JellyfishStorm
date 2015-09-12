package com.yellowbyte.jjss.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.yellowbyte.jjss.JellyGame;

public class JellyDesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width =960;
		config.height =540;
		new LwjglApplication(new JellyGame(), config);
	}
}

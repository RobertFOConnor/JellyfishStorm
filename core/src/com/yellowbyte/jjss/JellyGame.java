package com.yellowbyte.jjss;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.player.PlayerPrototype;
import com.yellowbyte.jjss.player.controllers.XBox360Pad;
import com.yellowbyte.jjss.screens.ScreenManager;
import com.yellowbyte.jjss.screens.SplashScreen;

public class JellyGame extends ApplicationAdapter {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	
	private SpriteBatch sb;	
	public static OrthoCamera camera;
	public static BitmapFont font;
	public static Music GAME_MUSIC;	
	public static int NUMBER_OF_LEVELS = 2;
	public static int UNLOCKED_LEVEL = 1;
	private FPSLogger log;
	
	private static Controller controller;
	public static boolean hasControllers = true;
	public static int playerCount = 1;
	public static Array<PlayerPrototype> playerSubmarines = new Array<PlayerPrototype>();
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		camera = new OrthoCamera();
		camera.resize();
		
		checkForController();
		
		ScreenManager.setScreen(new SplashScreen());
		log = new FPSLogger();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().update();
			ScreenManager.getCurrentScreen().render(sb);

			if (Gdx.input.isKeyPressed(Keys.BACK)) {
				ScreenManager.getCurrentScreen().goBack();
			}
		}
		log.log();
	}

	@Override
	public void resize(int w, int h) {
		
		if(ScreenManager.getCurrentScreen()!=null) 
			ScreenManager.getCurrentScreen().resize(w, h);
	}
	
	@Override
	public void dispose() {
		
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().dispose();
		
		sb.dispose();
		Assets.manager.dispose();
		Assets.manager = null;
	}
	
	@Override
	public void pause() {
		
		if(ScreenManager.getCurrentScreen()!=null)
			ScreenManager.getCurrentScreen().pause();
	}
	
	
	
	@Override
	public void resume() {
		Assets.load();
		while(!Assets.manager.update()){
	      }
		if(ScreenManager.getCurrentScreen()!=null) 
			ScreenManager.getCurrentScreen().resume();
	}

	public static void unlockNextLevel(int number) {
		if(number < NUMBER_OF_LEVELS && UNLOCKED_LEVEL <= number) {
			UNLOCKED_LEVEL++;
		}
	}
	
	public static Controller getController() {
		return controller;
	}
	
	public static void checkForController() {
		if(Controllers.getControllers().size == 0) {
            hasControllers = false;
        } else {
            controller = Controllers.getControllers().first();
        }
	}
	
	public static boolean clearNavigation() {
		if(!controller.getButton(XBox360Pad.BUTTON_A) && !controller.getButton(XBox360Pad.BUTTON_B)) {
			return true;
		}
		return false;
	}
}

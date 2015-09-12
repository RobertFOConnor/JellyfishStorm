package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.player.controllers.XBox360Pad;

public class TitleScreen implements Screen {

	private OrthoCamera camera;
	private String title, start;
	private Texture bg;
	private Controller controller;
	
	public TitleScreen() {
		title = "PROJECT SUB";
		start = "PRESS START";
		bg = Assets.manager.get(Assets.BG, Texture.class);
	}
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();		
		controller = JellyGame.getController();
	}

	@Override
	public void update() {
		camera.update();
		
		//ANDROID
		checkStartPress(Gdx.input.justTouched());
		
		//XBOX
		if (JellyGame.hasControllers) {
			checkStartPress (controller.getButton(XBox360Pad.BUTTON_START));
		}
		
		//KEYBOARD
		checkStartPress(Gdx.input.isKeyJustPressed(Keys.ENTER));
	}
	
	private void checkStartPress(boolean keyPressed) {
		if(keyPressed) {
			ScreenManager.setScreen(new MenuScreen());
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(bg, 0, 0);
		Fonts.TitleFont.draw(sb, title, JellyGame.WIDTH/2-Fonts.getWidth(Fonts.TitleFont, title)/2, 800);
		Fonts.MenuFont.draw(sb, start, JellyGame.WIDTH/2-Fonts.getWidth(Fonts.MenuFont, start)/2, 400);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.resize();
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goBack() {
		Gdx.app.exit();
		
	}
}

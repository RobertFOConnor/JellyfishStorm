package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.buttons.TextButton;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.media.SoundManager;

public class SettingsScreen implements Screen {

	private OrthoCamera camera;
	private Vector2 touch;
	
	private Texture bg;
	
	private TextButton musicButton, soundButton;
	private String title = "SETTINGS";
	
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();
		touch = new Vector2(0,0);
		bg = Assets.manager.get(Assets.BG, Texture.class);
		
		String musicSetting = "ON";
		String soundSetting = "ON";
		
		if(!SoundManager.musicEnabled) {
			musicSetting = "OFF";
		}
		
		if(!SoundManager.soundFXEnabled) {
			soundSetting = "OFF";
		}
		
		musicButton = new TextButton(musicSetting, Fonts.MenuFont, new Vector2(JellyGame.WIDTH-300, JellyGame.HEIGHT/2));
		soundButton = new TextButton(soundSetting, Fonts.MenuFont, new Vector2(JellyGame.WIDTH-300, JellyGame.HEIGHT/2-200));
		
	}
	@Override
	public void update() {
		if (Gdx.input.justTouched()) {
			touch = JellyGame.camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());

			if (musicButton.checkTouch(touch)) {
				SoundManager.musicEnabled = !SoundManager.musicEnabled;
				if (SoundManager.musicEnabled) {
					musicButton.setMessage("ON");
					//Assets.MAIN_THEME.play();
				} else {
					musicButton.setMessage("OFF");
					//Assets.MAIN_THEME.pause();
				}
			} else if (soundButton.checkTouch(touch)) {
				SoundManager.soundFXEnabled = !SoundManager.soundFXEnabled;
				if (SoundManager.soundFXEnabled) {
					soundButton.setMessage("ON");
				} else {
					soundButton.setMessage("OFF");
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(bg, 0, 0);
		musicButton.render(sb);
		soundButton.render(sb);
		Fonts.TitleFont.draw(sb, title, JellyGame.WIDTH/2-Fonts.getWidth(Fonts.TitleFont, title)/2, JellyGame.HEIGHT-100);
		Fonts.MenuFont.draw(sb, "Music: ", 300, JellyGame.HEIGHT/2);
		Fonts.MenuFont.draw(sb, "Sound FX: ", 300, JellyGame.HEIGHT/2-200);
		sb.end();
	}

	@Override
	public void resize(int w, int h) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void goBack() {
		ScreenManager.setScreen(new MenuScreen());
	}
}

package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.player.Player;

public class GameOverScreen implements Screen {

	private OrthoCamera camera;
	private Texture texture;
	private String message;
	private String scoreDisplay;
	
	public GameOverScreen(int score, boolean won) {
		if (won) {
			texture = Assets.manager.get(Assets.PLAYER, Texture.class);
			message = "LEVEL COMPLETE";
		} else {
			texture = Assets.manager.get(Assets.SHARK, Texture.class);
			message = "LEVEL FAILED";
		}
		
		scoreDisplay = "Score: "+score;
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();
	}

	@Override
	public void update() {
		camera.update();
		
		if(Gdx.input.justTouched()) {
			ScreenManager.setScreen(new MenuScreen());
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(texture, JellyGame.WIDTH / 2 - texture.getWidth() / 2, JellyGame.HEIGHT / 2 - texture.getHeight() / 2);
		Fonts.TitleFont.draw(sb, message, JellyGame.WIDTH/2-Fonts.getWidth(Fonts.TitleFont, message)/2, 800);
		Fonts.MenuFont.draw(sb, scoreDisplay, JellyGame.WIDTH/2-Fonts.getWidth(Fonts.MenuFont, scoreDisplay)/2, 400);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goBack() {
		ScreenManager.setScreen(new TitleScreen());
		
	}

}
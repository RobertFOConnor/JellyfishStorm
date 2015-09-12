package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.media.SoundManager;

public class SplashScreen implements Screen {

	private OrthoCamera camera;
	private Texture load_screen;
	private ShapeRenderer shapeRenderer;
	
	public SplashScreen() {
		camera = new OrthoCamera();
		camera.resize();
		shapeRenderer = new ShapeRenderer();		
		Assets.load();
		Fonts.loadFonts();
		
		load_screen = new Texture(Gdx.files.internal("splash_screen.png"));
		Gdx.input.setCatchBackKey(true);
	}
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();
	}

	@Override
	public void update() {
		camera.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(load_screen, 0, 0);
		sb.end();
		
		shapeRenderer.setProjectionMatrix(camera.combined);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.identity();
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(0, JellyGame.HEIGHT-50, JellyGame.WIDTH, 50);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(0, JellyGame.HEIGHT-50, (Assets.manager.getProgress()*JellyGame.WIDTH), 50);
		shapeRenderer.circle((Assets.manager.getProgress()*JellyGame.WIDTH), JellyGame.HEIGHT-25, 25);
		shapeRenderer.end();
		
		if(Assets.update()){ // DONE LOADING. SHOW TITLE SCREEN.
			
			SoundManager.switchMusic(Assets.MAIN_THEME);
			ScreenManager.setScreen(new TitleScreen());
		}			
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
		
	}

	@Override
	public void goBack() {
		
	}
}

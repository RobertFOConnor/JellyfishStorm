package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.buttons.Button;
import com.yellowbyte.jjss.buttons.TextButton;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.effects.ParticleManager;
import com.yellowbyte.jjss.gameobjects.BackgroundManager;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.gui.GUIManager;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;

public class GameScreen implements Screen {
	
	//MANAGERS
	private EntityManager entityManager;	
	private GUIManager guiManager;
	private BackgroundManager backgroundManager;
	public static ParticleManager pm;
	private OrthoCamera camera;
	private Vector2 touch;
	private Controller controller;
	private Button pauseButton;
	private TextButton resumeGame, exitGame, recalibrate, sensitivity;
	private boolean paused = false;
	
	private int levelNum;
	
	public GameScreen(int levelNum) {
		this.levelNum = levelNum;
	}
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();
				
		pm = new ParticleManager();
		
		entityManager = new EntityManager(levelNum, camera);
		backgroundManager = new BackgroundManager(levelNum, entityManager.getPlayers());
		guiManager = new GUIManager(camera, entityManager.getPlayers(), levelNum);
		
		touch = new Vector2();
		pauseButton = new Button(Assets.manager.get(Assets.PAUSE, Texture.class), new Vector2(10, JellyGame.HEIGHT-80));
		
		resumeGame = setupTextButton("Resume  Level", Fonts.MenuFont, 700);
		recalibrate = setupTextButton("Recalibrate  Device", Fonts.MenuFont, 500);
		sensitivity = setupTextButton("Sensitivity: "+entityManager.getPlayers().get(0).getSensitivity(), Fonts.MenuFont, 300);
		exitGame = setupTextButton("Exit  Level", Fonts.MenuFont, 100);
		
		String levTitle = "LEVEL "+entityManager.getLevel().getNumber();
		String levSub = entityManager.getLevel().getName().toUpperCase();
		
		GUIManager.setMessage(levTitle, JellyGame.HEIGHT/2+150, Fonts.TitleFont);
		GUIManager.setMessage(levSub, JellyGame.HEIGHT/2-50, Fonts.MenuFont);	
		
		controller = JellyGame.getController();
	}
	
	private TextButton setupTextButton(String s, BitmapFont f, float y) {
		return new TextButton(s, f, new Vector2(JellyGame.WIDTH/2-Fonts.getWidth(f, s)/2,y));
	}

	@Override
	public void update() {
		
		if (Gdx.input.justTouched()) {
			touch = JellyGame.camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());
						

			if (paused) {
				if (recalibrate.checkTouch(touch)) {
					entityManager.recalibratePlayer();
				} else if (sensitivity.checkTouch(touch)) {
					entityManager.getPlayers().get(0).addSensitivity();
					sensitivity.setMessage("Sensitivity: "+entityManager.getPlayers().get(0).getSensitivity());
				} else if (exitGame.checkTouch(touch)) {
					ScreenManager.setScreen(new MenuScreen());
				} else if(resumeGame.checkTouch(touch)) {
					setPaused(false);
				}		
			} else {
				if(pauseButton.checkTouch(touch)) {
					setPaused(true);
				}
			}
		}
		
		if(!paused) { //UPDATE LEVEL.
			
			backgroundManager.update();			
			camera.update();
			entityManager.update();	
			pm.update();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) { //For keyboard users.
			if(!paused) {
				setPaused(true);
			} else {
				setPaused(false);
			}
		}
		
		/*if (JellyGame.hasControllers) {
			if (controller.getButton(XBox360Pad.BUTTON_START)) {
				if(paused) {
					setPaused(false);
				} else {
					setPaused(true);
				}
			}
		}*/
	}

	@Override
	public void render(SpriteBatch sb) {		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		backgroundManager.render(sb);
		
		
		entityManager.render(sb);
		pm.render(sb);
		sb.end();
		
		
		guiManager.render(sb);
		
		sb.begin();
		//pauseButton.render(sb);
		
		
		if(paused) {
			sb.draw(Assets.manager.get(Assets.ALPHA, Texture.class), 0, 0, JellyGame.WIDTH, JellyGame.HEIGHT);
			Fonts.TitleFont.draw(sb, "PAUSED", JellyGame.WIDTH/2-Fonts.getWidth(Fonts.TitleFont, "PAUSED")/2, JellyGame.HEIGHT-100);
			resumeGame.render(sb);
			recalibrate.render(sb);
			sensitivity.render(sb);
			exitGame.render(sb);			
		}
		sb.end();
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
		entityManager.setPaused(paused);
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
		setPaused(true);
	}
}
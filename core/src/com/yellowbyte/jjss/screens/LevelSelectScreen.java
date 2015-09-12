package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.buttons.Button;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.player.controllers.XBox360Pad;

public class LevelSelectScreen implements Screen {

	private OrthoCamera camera;
	private Vector2 touch;
	private String title = "SELECT A LEVEL";
	private Texture bg;
	private Array<LevelButton> levelButtons;
	private Controller controller;
	private Texture xboxButtons;
	private boolean keysUp = false;
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();
		touch = new Vector2();
		controller = JellyGame.getController();
		
		
		bg = Assets.manager.get(Assets.BLUEPRINT_BG, Texture.class);
		
		levelButtons = new Array<LevelButton>();
		
		float levelY = JellyGame.HEIGHT/2+50;
		int levelCount = 1;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 5; j++) { 
				levelButtons.add(new LevelButton(new Vector2((JellyGame.WIDTH/4-200)*(j+1), levelY), levelCount));
				levelCount++;
			}
			levelY -= 300;
		}
		
		xboxButtons = Assets.manager.get(Assets.XBOX_MENU, Texture.class);
	}
	
	private class LevelButton extends Button {

		private int levelNum;
		private boolean unlocked = false;
		private boolean selected = true;
		private Color textColor;
		
		public LevelButton(Vector2 pos, int levelNum) {
			super(Assets.manager.get(Assets.LEVEL_BUTTON, Texture.class), pos);
			this.levelNum = levelNum;
			
			if(levelNum <= JellyGame.UNLOCKED_LEVEL) {
				unlocked = true;
			}
			
			textColor = Color.GRAY;
			if(unlocked) {
				textColor = Color.WHITE;
			}
		}
		
		@Override
		public void render(SpriteBatch sb) {
			sb.draw(texture, pos.x, pos.y);
			
			
			Fonts.LevelFont.setColor(textColor);
			Fonts.LevelFont.draw(sb, ""+levelNum, pos.x+80, pos.y+120);
		}
	}
	
	@Override
	public void update() {
		camera.update();
		
		if(Gdx.input.justTouched()) {
			touch = JellyGame.camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());
			
			
			for(LevelButton lb : levelButtons) {
				if(lb.checkTouch(touch) && lb.unlocked) {
					ScreenManager.setScreen(new GameScreen(lb.levelNum));
				}
			}
		}
		
		if (JellyGame.hasControllers) {
			
			//CHECK BUTTON HOLDS FROM PREVIOUS SCREEN.
			if(JellyGame.clearNavigation()) {
				keysUp = true;
			}
			
			if (controller.getButton(XBox360Pad.BUTTON_A) && keysUp) {
				ScreenManager.setScreen(new GameScreen(levelButtons.get(0).levelNum));
			} else if(controller.getButton(XBox360Pad.BUTTON_B) && keysUp) {
				ScreenManager.setScreen(new MenuScreen());
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(bg, 0, 0);
		Fonts.TitleFont.draw(sb, title, JellyGame.WIDTH/2-Fonts.getWidth(Fonts.TitleFont, title)/2, JellyGame.HEIGHT-100);
		
		for(LevelButton lb : levelButtons) {
			lb.render(sb);
		}
		
		if (JellyGame.hasControllers) {
			sb.draw(xboxButtons, JellyGame.WIDTH-570, 70);
		}
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
		
	}

	@Override
	public void goBack() {
		ScreenManager.setScreen(new MenuScreen());	
	}
}

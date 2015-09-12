package com.yellowbyte.jjss.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.buttons.SpriteButton;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.SoundManager;
import com.yellowbyte.jjss.player.controllers.XBox360Pad;
import com.yellowbyte.jjss.tween.SpriteAccessor;

public class MenuScreen implements Screen {

	private OrthoCamera camera;
	private Vector2 touch;
	private MenuButton playGame, settings, help;
	private Array<MenuButton> buttonArray;
	private MenuButton selectedButton;
	private Texture bg, xboxButtons;
	private TweenManager tweenManager;
	private long startTime, delta;
	private Controller controller;
	private float analogX;
	private boolean switched = false;
	
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();
		touch = new Vector2();
		controller = JellyGame.getController();
		bg = Assets.manager.get(Assets.MENU_BG, Texture.class);
		xboxButtons = Assets.manager.get(Assets.XBOX_MENU, Texture.class);
		
		playGame = new MenuButton(new TextureRegion(Assets.manager.get(Assets.PLAYGAME, Texture.class)), new Vector2(140, 526)) {

			@Override
			public void moveLeft() {
				setButtonSelected(settings);				
			}

			@Override
			public void moveRight() {
				setButtonSelected(help);
				
			}

			@Override
			public void activate() {
				ScreenManager.setScreen(new PlayerSelectScreen());
				
			}
			
		};
		settings = new MenuButton(new TextureRegion(Assets.manager.get(Assets.SETTINGS, Texture.class)), new Vector2(1384, 571)) {

			@Override
			public void moveLeft() {
				setButtonSelected(help);			
			}

			@Override
			public void moveRight() {
				setButtonSelected(playGame);
				
			}

			@Override
			public void activate() {
				ScreenManager.setScreen(new SettingsScreen());
				
			}
			
		};
		help = new MenuButton(new TextureRegion(Assets.manager.get(Assets.HELP, Texture.class)), new Vector2(919, 169)){

			@Override
			public void moveLeft() {
				setButtonSelected(playGame);		
			}

			@Override
			public void moveRight() {
				setButtonSelected(settings);
				
			}

			@Override
			public void activate() {
				//ScreenManager.setScreen(new HelpScreen());			
			}
			
		};
		buttonArray = new Array<MenuButton>();
		buttonArray.add(playGame);
		buttonArray.add(help);
		buttonArray.add(settings);
		
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		tweenManager = new TweenManager();
		
		setButtonSelected(playGame);	
	}
	
	public void setButtonSelected(MenuButton button) {
		tweenManager = new TweenManager();
		
		selectedButton = button;
		Tween.to(selectedButton, SpriteAccessor.SCALE_XY, 30f)
		.target(1f, 1f).ease(TweenEquations.easeOutElastic)
		.start(tweenManager);
		
		Tween.to(selectedButton, SpriteAccessor.TINT, 30f)
		.target(1f, 1f, 1f).ease(TweenEquations.easeOutBack)
		.start(tweenManager);
		
		startTime = TimeUtils.millis();
		
		
		for(MenuButton b : buttonArray) {
			if(!b.equals(selectedButton)) {
				b.setColor(Color.LIGHT_GRAY);
				b.setScale(0.8f);
			}
		}
				
		switched = true;
	}
	
	@Override
	public void update() {
		camera.update();
		
		//XBOX 360 CONTROLLER.
		if (JellyGame.hasControllers) {
			analogX = controller.getAxis(XBox360Pad.AXIS_LEFT_X);

			if (analogX < 0.5f && analogX > -0.5f) {
				switched = false;
			}
			
			moveSelection((analogX > 0.5f)&& !switched, (analogX < -0.5f)&& !switched);
			confirmSelection(controller.getButton(XBox360Pad.BUTTON_A));
			
			if (controller.getButton(XBox360Pad.BUTTON_B)) {
				ScreenManager.setScreen(new TitleScreen());
			}
		}
		
		//KEYBOARD.
		moveSelection(Gdx.input.isKeyJustPressed(Keys.RIGHT), Gdx.input.isKeyJustPressed(Keys.LEFT));		
		confirmSelection(Gdx.input.isKeyJustPressed(Keys.ENTER));
		
		
		
		//ANDROID.
		if(Gdx.input.justTouched()) {
			touch = JellyGame.camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());
			
			if(playGame.checkTouch(touch)) {
				playGame.activate();
			} else if(settings.checkTouch(touch)) {				
				settings.activate();
			} else if(help.checkTouch(touch)) {
				help.activate();
			}
		}
	}

	

	@Override
	public void render(SpriteBatch sb) {
		delta = (TimeUtils.millis()-startTime+1000)/1000; 
		tweenManager.update(delta); 
		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(bg, 0, 0);
		if (JellyGame.hasControllers) {
			sb.draw(xboxButtons, JellyGame.WIDTH-570, 70);
		}
		
		playGame.draw(sb);
		settings.draw(sb);		
		help.draw(sb);
		sb.end();
	}
	
	private void moveSelection(boolean checkRight, boolean checkLeft) {
		if(checkRight) {
			SoundManager.play(Assets.manager.get(Assets.MENU_SELECT, Sound.class));
			selectedButton.moveRight();
		} else if(checkLeft) {
			SoundManager.play(Assets.manager.get(Assets.MENU_SELECT, Sound.class));
			selectedButton.moveLeft();
		}
	}
	
	private void confirmSelection(boolean keyPressed) {
		if(keyPressed) {
			selectedButton.activate();
		}	
	}
	
	public abstract class MenuButton extends SpriteButton {

		public MenuButton(TextureRegion region, Vector2 pos) {
			super(region, pos);

		}
		
		public abstract void moveLeft();
		
		public abstract void moveRight();
		
		public abstract void activate();

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

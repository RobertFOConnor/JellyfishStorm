package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.player.PlayerPrototype;
import com.yellowbyte.jjss.player.SubmarineCreator;
import com.yellowbyte.jjss.player.controllers.XBox360Pad;

public class PlayerSelectScreen implements Screen {

	private OrthoCamera camera;
	private ShapeRenderer shapeRenderer;
	private Vector2 touch;
	private String title = "CHOOSE A SUBMARINE";
	private Texture bg, subBox;
	private Controller controller;
	private Texture xboxButtons;
	private Array<SelectionBox> selectionBoxes;
	private final String[] submarines = new String[]{"yellow_sub", "pink_sub"};	
	private PlayerPrototype currSub;	
	private boolean keysUp = false;
	
	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();
		touch = new Vector2();
		shapeRenderer = new ShapeRenderer();
		controller = JellyGame.getController();
		bg = Assets.manager.get(Assets.BLUEPRINT_BG, Texture.class);
		xboxButtons = Assets.manager.get(Assets.XBOX_MENU, Texture.class);
		subBox = Assets.manager.get(Assets.SUB_SELECT, Texture.class);
		
		JellyGame.playerCount = 1;
		JellyGame.playerSubmarines = new Array<PlayerPrototype>();
		selectionBoxes = new Array<SelectionBox>();
		selectionBoxes.add(new SelectionBox(subBox, 1, JellyGame.WIDTH/2-subBox.getWidth()/2, 195));
	}
	
	@Override
	public void update() {
		camera.update();
		
		for(SelectionBox s : selectionBoxes) {
			s.update();
		}
		
		if(Gdx.input.justTouched()) {
			touch = JellyGame.camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());
			advanceScreen();
		}
		
		if (JellyGame.hasControllers) {
			
			//CHECK BUTTON HOLDS FROM PREVIOUS SCREEN.
			if(JellyGame.clearNavigation()) {
				keysUp = true;
			}
			
			if (controller.getButton(XBox360Pad.BUTTON_A) && keysUp) {
				advanceScreen();
			} else if(controller.getButton(XBox360Pad.BUTTON_B) && keysUp) {
				ScreenManager.setScreen(new MenuScreen());
			}
			
			
			
			if(Gdx.input.isKeyJustPressed(Keys.ENTER) && JellyGame.playerCount == 1) { // ADD PLAYER 2 BOX.
				JellyGame.playerCount = 2;
				selectionBoxes.get(0).x = 115;
				selectionBoxes.add(new SelectionBox(subBox, 2, JellyGame.WIDTH-subBox.getWidth()-115, 195));
			}
		} else {
			
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) { 
				advanceScreen();
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(bg, 0, 0);
		Fonts.TitleFont.draw(sb, title, JellyGame.WIDTH/2-Fonts.getWidth(Fonts.TitleFont, title)/2, JellyGame.HEIGHT-100);
		
		for(SelectionBox s : selectionBoxes) {
			s.render(sb);
		}
		
		if (JellyGame.hasControllers) {
			sb.draw(xboxButtons, JellyGame.WIDTH-570, 70);
		}
		sb.end();
	}
	
	public void advanceScreen() {
		for(SelectionBox s : selectionBoxes) {
			JellyGame.playerSubmarines.add(s.currSub);
		}
		ScreenManager.setScreen(new LevelSelectScreen());
	}
	
	private class SelectionBox {
		
		private Texture texture;
		private PlayerPrototype currSub;
		private float x, y = 0;
		private int playerNumber;
		private int subInc = 0;
		
		public SelectionBox(Texture texture, int playerNumber, float x, float y) {
			this.texture = texture;
			this.playerNumber = playerNumber;
			this.x = x;
			this.y = y;
			currSub = SubmarineCreator.createPrototype("pink_sub");
		}
		
		public void update() {
			if (JellyGame.hasControllers && playerNumber == 1) {
				float analogX = controller.getAxis(XBox360Pad.AXIS_LEFT_X);
				checkMoveLeft(analogX < -0.5f);
				checkMoveRight(analogX > 0.5f);
				
			} else {
				checkMoveLeft(Gdx.input.isKeyJustPressed(Keys.LEFT));
				checkMoveRight(Gdx.input.isKeyJustPressed(Keys.RIGHT));
			}
		}


		public void render(SpriteBatch sb) {
			sb.draw(texture, x, y);
			sb.draw(Assets.manager.get(currSub.image+".png", Texture.class), x+222, y+377);
			Fonts.SubFont.draw(sb, currSub.name.toUpperCase(), x+120, y+560);
			Fonts.MenuFont.draw(sb, ""+playerNumber, x+575, y+590);
			sb.end();
			
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.identity();
			
			drawStat(currSub.health, 237);
			drawStat(currSub.speed, 137);
			drawStat(currSub.power/5, 37);
			
			
			shapeRenderer.end();
			sb.begin();
		}
		
		public void drawStat(int stat, float posY) {
			for(int i = 0; i < stat; i++) {
				shapeRenderer.rect(x+235+(int)(i*34.6), y+posY, 22, 61);
			}
		}
		
		public void checkMoveLeft(boolean moveLeft) {
			if(moveLeft) {
				
				if(subInc > 0) {
					subInc--;
					currSub = SubmarineCreator.createPrototype(submarines[subInc]);
				}
			}
		}
		
		public void checkMoveRight(boolean moveRight) {
			if(moveRight) {
				
				if(subInc < submarines.length-1) {
					subInc++;
					currSub = SubmarineCreator.createPrototype(submarines[subInc]);
				}
			}
		}
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
		
	}

}

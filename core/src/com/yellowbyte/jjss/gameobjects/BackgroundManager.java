package com.yellowbyte.jjss.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.player.Player;

public class BackgroundManager {
	
	//BACKGROUND VARS
	private float bgXPos = 0;
	private float scrollSpeed = 1.4f;
	private ShapeRenderer shapeRenderer;
	private Texture bg_shine, bg_far;
	private BackgroundObject bg_rock, bg_rock2, bg_rock3;
	private Color topColor, bottomColor;
	private Array<Player> players;
	
	public BackgroundManager(int level, Array<Player> players) {
		this.players = players;
		
		bg_shine = Assets.manager.get(Assets.BG_SHINE, Texture.class);
		bg_far = Assets.manager.get(Assets.BG_FAR, Texture.class);
		shapeRenderer = new ShapeRenderer();
		
		int TColors[] = {12, 172, 167}; //TOP OF BG GRADIENT
		int BColors[] = {0, 91, 166}; //BOTTOM OF BG GRADIENT
		
		if(level == 1) {
			topColor = new Color((float)(TColors[0]/255), (float)TColors[1]/255, (float)TColors[2]/255, 1);
			bottomColor = new Color((float)(BColors[0]/255), (float)BColors[1]/255, (float)BColors[2]/255, 1);
		} else if(level == 2) {
			topColor = new Color((float)(255/255), (float)74/255, (float)203/255, 1);
			bottomColor = new Color((float)(109/255), (float)0/255, (float)121/255, 1);
		} else {
			topColor = new Color((float)(107/255), (float)74/255, (float)255/255, 1);
			bottomColor = new Color((float)(51/255), (float)0/255, (float)0/255, 1);	
		}
	
		bg_rock = new BackgroundObject(Assets.manager.get(Assets.BG_ROCK, Texture.class), new Vector2(0, 0));
		bg_rock2 = new BackgroundObject(Assets.manager.get(Assets.BG_ROCK2, Texture.class), new Vector2(700, 0));
		bg_rock3 = new BackgroundObject(Assets.manager.get(Assets.BG_ROCK, Texture.class), new Vector2(1700, 0));
	}
	
	public void update() {		
		bgXPos -= scrollSpeed;
		
		if(bgXPos <= -JellyGame.WIDTH) {
			bgXPos = 0;
		}
		
		bg_rock.update();
		bg_rock2.update();
		bg_rock3.update();
	}

	public void render(SpriteBatch sb) {
		sb.end();
		shapeRenderer.setProjectionMatrix(JellyGame.camera.combined);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.identity();
		shapeRenderer.setColor(Color.DARK_GRAY);
		
		//DRAW CHARGED SHOT
		shapeRenderer.rect(0, 0, JellyGame.WIDTH, JellyGame.HEIGHT);
		shapeRenderer.rect(0f, 0f,(float) JellyGame.WIDTH, (float) JellyGame.HEIGHT, bottomColor, bottomColor, topColor, topColor);
		
		
		shapeRenderer.end();
		
		sb.begin();
		
		float bgYPos;
		
		if(players.size > 1) {		
			bgYPos = -(float) (((players.get(0).getPosition().y+players.get(1).getPosition().y)/2)/18);
		} else {
			bgYPos = -(float) (players.get(0).getPosition().y/18);
		}
		sb.draw(bg_far, bgXPos, bgYPos);
		sb.draw(bg_far, bgXPos+JellyGame.WIDTH, bgYPos);
		
		sb.draw(bg_shine, bgXPos, 0);
		sb.draw(bg_shine, bgXPos+JellyGame.WIDTH, 0);
		
		bg_rock.render(sb);
		bg_rock2.render(sb);
		bg_rock3.render(sb);
	}

	private class BackgroundObject extends Entity {
		
		public BackgroundObject(Texture texture, Vector2 pos) {
			super(texture, pos, new Vector2());
		}
		
		@Override
		public void update() {
			pos.x -= scrollSpeed*2;

			if(players.size > 1) {		
				pos.y = -(float) (((players.get(0).getPosition().y+players.get(1).getPosition().y)/2)/9);
			} else {
				pos.y = -(float) (players.get(0).getPosition().y/9);
			}
			
			
			if (pos.x < -1000) {
				pos.x = JellyGame.WIDTH;
			}
		}
	}
}

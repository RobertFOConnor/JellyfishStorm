package com.yellowbyte.jjss.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.player.Player;

public class GUIManager {

	private OrthoCamera camera;
	private Array<Player> players;
	private int levelNumber;
	private ShapeRenderer shapeRenderer;
	private final Texture gunUpgrade, player_gui;
	private static Array<GUIMessage> messages;

	
	public GUIManager(OrthoCamera camera, Array<Player> players, int levelNumber) {
		this.camera = camera;
		this.players = players;
		this.levelNumber = levelNumber;		
		shapeRenderer = new ShapeRenderer();
		gunUpgrade = Assets.manager.get(Assets.GUN_UPGRADE, Texture.class);	
		player_gui = Assets.manager.get(Assets.PLAYER_GUI, Texture.class);	
		messages = new Array<GUIMessage>();
	}
	
	public void update() {
	}
	
	public void render(SpriteBatch sb) {
		
		shapeRenderer.setProjectionMatrix(camera.combined);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.identity();
		shapeRenderer.setColor(Color.DARK_GRAY);

		//DRAW GUN UPGRADES
		//shapeRenderer.setColor(Color.MAROON);
		//shapeRenderer.rect(JellyGame.WIDTH/2-150, 50, players.get(0).getGun().getGunUpgrade()*50, 50);
		
		shapeRenderer.end();
		
		sb.begin();
		//Fonts.GUIFont.draw(sb, "Level: "+levelNumber, 100, JellyGame.HEIGHT-50);
		
		for(int i = 0; i < players.size; i++) {
			Player player = players.get(i);
			float guiX = 70+(i*(player_gui.getWidth()+70));
			float guiY = 30;
			
			
			
			sb.end();
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.identity();
			
			shapeRenderer.setColor(1, 1, 0.7f, 1);
			shapeRenderer.rect(guiX+48, guiY+40, player.getLives()*(100/player.getMaxLives()), 30);
			
			if (player.getGun().getPowerShot() < player.getGun().getPowerShotTime()) {
				shapeRenderer.setColor(1, 1, 0.7f, 1);
			} else {
				shapeRenderer.setColor(Color.RED);
			}
			shapeRenderer.rect(guiX+48, guiY, player.getGun().getPowerShot() / (float)(player.getGun().getPowerShotTime()/100), 30);
			
			
			shapeRenderer.end();
			sb.begin();
			
			sb.draw(player_gui, guiX, guiY);
			Fonts.GUIFont.draw(sb, ""+player.getScore(), JellyGame.WIDTH-50-Fonts.getWidth(Fonts.GUIFont, ""+player.getScore()), JellyGame.HEIGHT-50*(i+1));
			
		}
		
		//sb.draw(gunUpgrade, JellyGame.WIDTH/2-gunUpgrade.getWidth()/2, 30);
		
		for(GUIMessage mess : messages) {
			mess.update();
			mess.draw(sb);
			
			if(mess.isFinished()) {
				messages.removeValue(mess, false);
			}
		}
		
		sb.end();
	}

	public static void setMessage(String s, float h, BitmapFont font) {
		messages.add(new GUIMessage(s, new Vector2(JellyGame.WIDTH/2-font.getBounds(s).width/2, h), font));
	}
	
	public static void addItemMessage(String s, Vector2 pos, BitmapFont font) {
		messages.add(new GUIMessage(s, new Vector2(pos.x-Fonts.GUIFont.getBounds(s).width/2, pos.y+100), font));
	}
}

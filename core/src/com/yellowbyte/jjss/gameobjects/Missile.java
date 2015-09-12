package com.yellowbyte.jjss.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.media.Assets;

public class Missile extends Entity {
	
	private EntityManager em;
	private boolean isEnemies = false;
	private int power;

	public Missile(Vector2 pos, int power, Vector2 dir, String img, boolean isEnemies, EntityManager em) {
		super(Assets.manager.get(img, Texture.class), pos, dir);
		
		this.power = power;
		this.isEnemies = isEnemies;
		this.em = em;
	}

	@Override
	public void update() {
		
		// Player-seeking missiles.
		/*if (pos.x > em.getPlayer().getPosition().x && isEnemies) {
			if ((pos.y + (texture.getHeight() / 2) - em.getPlayer()
					.getMidPoint().y) > 5) {
				direction.y = -3;
			} else if ((pos.y + (texture.getHeight() / 2) - em.getPlayer()
					.getMidPoint().y) < -5) {
				direction.y = 3;
			} else {
				direction.y = 0;
			}
		}*/
		
		pos.add(direction);
	}
	
	public boolean checkEnd() {
		return pos.x >= JellyGame.WIDTH || pos.x < -200;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int i) {
		power = i;
		
	}
	
	public boolean isEnemies() {
		return isEnemies;
	}
}
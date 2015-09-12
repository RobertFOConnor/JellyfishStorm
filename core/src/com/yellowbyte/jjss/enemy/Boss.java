package com.yellowbyte.jjss.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.gameobjects.MissileFactory.MissileType;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.player.Player;

public class Boss extends Enemy {

	private boolean hasEntered = false; //Has the boss entered the scene?
	private Array<Player> players;
	private int targetedPlayerIndex = 0;
	
	public Boss(Texture texture, int health, int worth, boolean canShoot,
			Vector2 pos, Vector2 direction, EntityManager em) {
		super(texture, health, worth, canShoot, pos, direction, em);
		
		players = em.getPlayers();
	}
	
	@Override
	public void update() {		
		
		if(pos.x < 1300 && !hasEntered) {
			direction.x = 0;
			hasEntered = true;
		}
		
		if((pos.y+(texture.getHeight()/2) - players.get(targetedPlayerIndex).getMidPoint().y) > 5) {
			direction.y = -4;
		} else if((pos.y+(texture.getHeight()/2) - players.get(targetedPlayerIndex).getMidPoint().y) < -5){
			direction.y = 4;
		} else {
			direction.y = 0;
		}
		
		
		pos.add(direction);
		
		
		if (pos.x <= -texture.getWidth()-400 || pos.y < -400 || pos.y > JellyGame.HEIGHT+400 || pos.x > JellyGame.WIDTH+400) {
			this.setHealth(0);
		}
		
		
		if (canShoot) {
			if ((int) (Math.random() * 30) == 1) {
				
				em.addEntity(missileFactory.getMissile(
						MissileType.enemybasic,
						pos.cpy().add(
								texture.getWidth()
										/ 2
										- Assets.manager.get(Assets.MISSILE,
												Texture.class).getWidth() / 2,
								texture.getHeight() / 2 - 50), em));
			}
		}
		
		//SWITCH TARGET
		if(players.size > 1) {
			if ((int) (Math.random() * 100) == 1) {
				if(targetedPlayerIndex == 0) {
					targetedPlayerIndex = 1;
				} else {
					targetedPlayerIndex = 0;
				}
			}
		}
		
		
		stateTime += Gdx.graphics.getDeltaTime();
	}

}

package com.yellowbyte.jjss.wave;

import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.gameobjects.EntityManager;

public class WaveFactory {
	
	public Wave build(String enemyType, int amount, int speed, String pattern, float Y, EntityManager em) {

		if (pattern.equals("Straight")) {
			return new Wave(enemyType, amount, 400, new Vector2(JellyGame.WIDTH, Y), new Vector2(-speed, 0), new Vector2(0,0), em);
			
		} else if (pattern.equals("Wall")) {
			return new Wave(enemyType, amount, 0, new Vector2(JellyGame.WIDTH, Y), new Vector2(-speed, 0), new Vector2(0,-200), em);
			
		} else if (pattern.equals("Diagonal")) {
			int angle = 2;
			if(Y > JellyGame.HEIGHT/2) {
				angle = -2;
			}
			return new Wave(enemyType, amount, 400, new Vector2(JellyGame.WIDTH, Y), new Vector2(-speed, angle), new Vector2(0, 0), em);
		
		} else if(pattern.equals("Boss")) {
			return new BossWave(enemyType, new Vector2(JellyGame.WIDTH, Y), new Vector2(-speed, 0), em);			
		}
		
		return null;
	}
}

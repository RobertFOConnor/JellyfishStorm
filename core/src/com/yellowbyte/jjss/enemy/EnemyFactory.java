package com.yellowbyte.jjss.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.media.Assets;

public class EnemyFactory {

	public Enemy build(String type, Vector2 pos, Vector2 dir, EntityManager em) {

		Enemy enemy;

		if(type.equals("WHALE")) {
			enemy = new Enemy(Assets.manager.get(Assets.WHALE, Texture.class), 1, 100, true, pos, dir, em);
		} else if(type.equals("JELLY")) {
			enemy = new Enemy(Assets.manager.get(Assets.JELLY, Texture.class), 2, 300, true, pos, dir, em);
		} else {
			enemy = new Enemy(Assets.manager.get(Assets.SHARK, Texture.class), 1, 100, true, pos, dir, em);
		}

		return enemy;
	}
}

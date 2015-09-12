package com.yellowbyte.jjss.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.media.Assets;

public class BossEnemyFactory {

	public Boss build(String type, Vector2 pos, Vector2 dir, EntityManager em) {

		Boss enemy;

		if(type.equals("BOSS_EEL")) {
			enemy = new Boss(Assets.manager.get(Assets.BOSS_EEL, Texture.class), 100, 10000, true, pos, dir, em);
		} else {
			enemy = new Boss(Assets.manager.get(Assets.BOSS_EEL, Texture.class), 100, 10000, true, pos, dir, em);
		}

		return enemy;
	}
}

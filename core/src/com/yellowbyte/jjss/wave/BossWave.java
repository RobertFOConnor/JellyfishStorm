package com.yellowbyte.jjss.wave;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.enemy.BossEnemyFactory;
import com.yellowbyte.jjss.enemy.Enemy;
import com.yellowbyte.jjss.gameobjects.EntityManager;

public class BossWave extends Wave {

	private BossEnemyFactory enemyFactory;
	
	// WAVE CONSTRUCTOR FOR BOSS ENEMIES
	public BossWave(String bossType, Vector2 pos, Vector2 dir, EntityManager em) {
		super(bossType, 1, 400, pos, dir, new Vector2(0, 0), em);
		enemies = new Array<Enemy>();
		enemyFactory = new BossEnemyFactory();

		enemies.add(enemyFactory.build(bossType, pos, dir.cpy(), em));
		this.em = em;
	}
}

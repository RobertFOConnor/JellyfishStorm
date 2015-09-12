package com.yellowbyte.jjss.wave;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.enemy.Enemy;
import com.yellowbyte.jjss.enemy.EnemyFactory;
import com.yellowbyte.jjss.gameobjects.EntityManager;

public class Wave {

	protected EntityManager em;
	protected Array<Enemy> enemies;
	
	private long startTime;
	private long timeGap;  //Time between each enemy.
	private boolean finished = false;
	protected EnemyFactory enemyFactory;
	
	public Wave(String enemyType, int enemyCount, long timeGap, Vector2 pos, Vector2 dir, Vector2 changePos, EntityManager em) {
		enemies = new Array<Enemy>();		
		enemyFactory = new EnemyFactory();
		
		for(int i = 0; i < enemyCount; i++) {
			Vector2 enemyPosition = pos.cpy();
			enemies.add(enemyFactory.build(enemyType, enemyPosition, dir.cpy(), em));
		}
		this.timeGap = timeGap;
		this.em = em;
	}
	
	
	

	public boolean isFinished() {
		return finished;
	}

	public void update() {
		if((System.nanoTime()-startTime)/1000000 > timeGap) {
			
			if(enemies.size > 0) { // ADD NEXT ENEMY TO ENTITY MANAGER.
				em.addEntity(enemies.get(0));
				enemies.removeIndex(0);
				startTime = System.nanoTime();
			} else {
				if(em.getEnemies().size <= 0) { // END OF WAVE.
					finished = true;
				}
			}
		}		
	}
}

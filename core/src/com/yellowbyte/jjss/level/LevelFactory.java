package com.yellowbyte.jjss.level;

import java.util.ArrayList;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.wave.WaveFactory;
import com.yellowbyte.jjss.wave.WavePrototype;


public class LevelFactory {

	private int id;
	private String name;
	
	private ArrayList<WavePrototype> waves;
	
	public Level createLevel(EntityManager em) {
		WavePrototype w = null;
		Level level = new Level(id);
		level.setDescription(name);
		WaveFactory waveFactory = new WaveFactory();

		int enemyCount = 0;
		
		for (int i = 0; i < waves.size(); i++) {
			w = waves.get(i);

			level.addWave(waveFactory.build(w.enemyType, w.amount, w.speed, w.pattern,
					w.position * 100, em));
			
			enemyCount += w.amount;
		}
		
		level.setEnemyCount(enemyCount);

		return level;
	}
}

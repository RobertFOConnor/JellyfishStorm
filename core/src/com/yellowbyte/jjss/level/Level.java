package com.yellowbyte.jjss.level;

import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.gui.GUIManager;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.media.SoundManager;
import com.yellowbyte.jjss.wave.BossWave;
import com.yellowbyte.jjss.wave.Wave;

public class Level {
	
	private int number;
	private String name;
	private Array<Wave> waves;
	private int enemyCount, enemiesKilled = 0;
	private Wave currWave;
	private boolean finished = false;
	
	private long waitTime = 5000;
	private long startTime = 0;
	private long finishTime;
	private boolean started = false;
	
	public Level(int number) {
		this.number = number;
		waves = new Array<Wave>();
	}
	
	public Level(int number, String name) {
		this.number = number;
		this.name = name;
		
		waves = new Array<Wave>();
	}
	
	public void addWave(Wave wave) {
		waves.add(wave);
	}
	
	public void startLevel() {
		currWave = waves.get(0);
		waves.removeIndex(0);
		startTime = System.nanoTime();
	}
	
	
	public void update() {
		
		if(started) {

			if (!currWave.isFinished()) {
				currWave.update();
			} else {

				if (waves.size > 0) { // START NEXT WAVE
					currWave = waves.get(0);
					
					if(currWave instanceof BossWave) {
						SoundManager.switchMusic(Assets.BOSS_THEME);
					}
					
					waves.removeIndex(0);
				} else {					
					
					if(finishTime != 0) {
						if ((System.nanoTime() - finishTime) / 1000000 >= waitTime) {
							finished = true;
						}	
					} else {
						finishTime = System.nanoTime();
						GUIManager.setMessage("LEVEL COMPLETE! (" + enemiesKilled+"/"+enemyCount+")",  JellyGame.HEIGHT/2+100, Fonts.TitleFont);	
					}
				}
			}
		} else {
			if ((System.nanoTime() - startTime) / 1000000 >= waitTime) {
				started = true;
			}
		}
	}
	
	
	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setDescription(String name) {
		this.name = name;	
	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public void setEnemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}
}

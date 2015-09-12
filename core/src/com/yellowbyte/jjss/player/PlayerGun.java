package com.yellowbyte.jjss.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.gameobjects.Missile;
import com.yellowbyte.jjss.gameobjects.MissileFactory;
import com.yellowbyte.jjss.gameobjects.MissileFactory.MissileType;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.SoundManager;

public class PlayerGun {

	private EntityManager entityManager;
	
	//POWER-SHOT VARS
	private long lastFire;
	private int gunUpgrade = 0;
	private long startShotTime = 0;
	private long powerShotTime = 800;
	private boolean chargingShot = false;
	private boolean charged = false;
	private Sound chargedSound = Assets.manager.get(Assets.CHARGED, Sound.class);
	private Sound chargingSound = Assets.manager.get(Assets.CHARGING, Sound.class);
	
	public PlayerGun(EntityManager entityManager) {
		this.entityManager = entityManager;
		startShotTime = System.nanoTime();
	}
	
	public void setChargingShot(boolean chargingShot) {
		this.chargingShot = chargingShot;
		startShotTime = System.nanoTime();
		
		if(chargingShot) {
			SoundManager.play(chargingSound);
			//SoundManager.loop(chargingSound);
		} else {
			chargingSound.stop();
		}
	}

	public long getPowerShot() {
		if(chargingShot) {
			long powerAmount = (System.nanoTime()-startShotTime)/1000000;
			if(powerAmount < powerShotTime) {
				return powerAmount;
			} else {
				if(!charged) {
					chargingSound.stop();
					
					SoundManager.play(chargedSound);
					//SoundManager.loop(chargedSound);
					charged = true;
				}		
				return powerShotTime;
			}
		}
		return 0;
	}
	
	
	public long getPowerShotTime() {
		return powerShotTime;
	}

	public void resetCharge() {
		setChargingShot(false);
		charged = false;
		chargedSound.stop();
	}
	
	public boolean isCharging() {
		return chargingShot;
	}
	
	public void loseUpgrade() {
		if(gunUpgrade > 0) {
			gunUpgrade--;
		}
	}
	
	public int getGunUpgrade() {
		return gunUpgrade;
	}
	
	public void upgradeGuns() {
		if(gunUpgrade < 6) {
			gunUpgrade++;
		}
	}
	
	
	public void releaseShot(Texture texture, Vector2 pos) {
		chargingSound.stop();
		if (System.currentTimeMillis() - lastFire >= 300) {
			
			MissileFactory missileFactory = new MissileFactory();
			Missile missile;
			float missX = texture.getWidth()/2
					-Assets.manager.get(Assets.MISSILE, Texture.class).getWidth()/2;
			float missY = texture.getHeight()/2;
			
			EntityManager em = entityManager;
			
			if((System.nanoTime()-startShotTime)/1000000 >= powerShotTime) {
				
				if(gunUpgrade == 6) {
					missile = missileFactory.getMissile(MissileType.huge, pos.cpy().add(texture.getWidth()/2
							-Assets.manager.get(Assets.MISSILE_HUGE, Texture.class).getWidth()/2, 
							texture.getHeight()/2-40), em);	
				} else {
					missile = missileFactory.getMissile(MissileType.charged, pos.cpy().add(texture.getWidth()/2
							-Assets.manager.get(Assets.MISSILE_BIG, Texture.class).getWidth()/2, 
							texture.getHeight()/2-40), em);						
				}
				
				entityManager.addEntity(missile);				
				SoundManager.play(Assets.manager.get(Assets.MISSILE_BIG_SOUND, Sound.class));
				
			} else {
				if(gunUpgrade < 1) {
					missile = missileFactory.getMissile(MissileType.basic, pos.cpy().add(missX, 
							missY-40), em);	
					entityManager.addEntity(missile);
				} else if(gunUpgrade < 3) {
					missile = missileFactory.getMissile(MissileType.basic, pos.cpy().add(missX, 
							missY-60), em);	
					entityManager.addEntity(missile);
					
					missile = missileFactory.getMissile(MissileType.basic, pos.cpy().add(missX,
							missY-10), em);	
					entityManager.addEntity(missile);
					
					if(gunUpgrade == 2) {
						missile = missileFactory.getMissile(MissileType.back, pos.cpy().add(missX,
								missY-20), em);	
						entityManager.addEntity(missile);
					}
					
				} else if(gunUpgrade >= 3) {
					missile = missileFactory.getMissile(MissileType.pill, pos.cpy().add(missX,
							missY-20), em);	
					entityManager.addEntity(missile);
					
					missile = missileFactory.getMissile(MissileType.pillback, pos.cpy().add(missX,
							missY-20), em);	
					entityManager.addEntity(missile);
					
					
					if(gunUpgrade >= 4) {
						missile = missileFactory.getMissile(MissileType.topball, pos.cpy().add(missX,
								missY-40), em);	
						entityManager.addEntity(missile);
					}
					
					if(gunUpgrade >= 5) {
						missile = missileFactory.getMissile(MissileType.botball, pos.cpy().add(missX,
								missY-40), em);	
						entityManager.addEntity(missile);
					}
				}
				SoundManager.play(Assets.manager.get(Assets.MISSILE_SOUND, Sound.class));
			}			
			
			
			lastFire = System.currentTimeMillis();				
			startShotTime = 0;
		}
		chargingShot = false;	
		charged = false;
		chargedSound.stop();
	}
}

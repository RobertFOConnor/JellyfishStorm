package com.yellowbyte.jjss.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.enemy.Enemy;
import com.yellowbyte.jjss.gui.GUIManager;
import com.yellowbyte.jjss.level.Level;
import com.yellowbyte.jjss.level.LevelCreator;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.media.SoundManager;
import com.yellowbyte.jjss.player.Player;
import com.yellowbyte.jjss.player.PlayerPrototype;
import com.yellowbyte.jjss.player.SubmarineCreator;
import com.yellowbyte.jjss.screens.GameScreen;
import com.yellowbyte.jjss.screens.LevelSelectScreen;
import com.yellowbyte.jjss.screens.ScreenManager;

public class EntityManager {

	private final Array<Entity> entities = new Array<Entity>();
	private final Array<Player> players =  new Array<Player>();	
	private Level level;
	private ItemFactory itemFactory = new ItemFactory();
	

	public EntityManager(int levelNum, OrthoCamera camera) {
		
		int i = 1;
		for(PlayerPrototype p : JellyGame.playerSubmarines) {
			players.add(SubmarineCreator.create(i, p, this));	
			i++;
		}
		
		//players.add(new Player("pinkie", new Vector2(200, JellyGame.HEIGHT/2-400), new Vector2(0, 0), this));	
		level = LevelCreator.build(this, levelNum);
		level.startLevel();
	}

	public void update() {
		
		for (Entity e : entities) {
			e.update();
		}
			
		for(Player p : players) {
			p.update();
		}
		checkCollisions();
		
		if(!level.isFinished()) {
			level.update();
		} else {
			if(getEnemies().size <= 0) { // LEVEL COMPLETE!
				SoundManager.switchMusic(Assets.MAIN_THEME);
				JellyGame.unlockNextLevel(level.getNumber());
				ScreenManager.setScreen(new LevelSelectScreen());
				Gdx.input.setInputProcessor(null);
			}			
		}
	}

	public void render(SpriteBatch sb) {
		for (Entity e : entities) {
			e.render(sb);
		}
		
		for(Player p : players) {
			p.render(sb);
		}
	}

	private void checkCollisions() {
		
		for (Enemy e : getEnemies()) {
			for (Missile m : getMissiles()) {
				if (e.getBounds().overlaps(m.getBounds()) && !m.isEnemies()) { //DID MISSILE HIT AN ENEMY?

					int eHP = e.getHealth();

					e.setHealth(e.getHealth() - m.getPower());
					m.setPower(m.getPower() - eHP);
					
					if (e.getHealth() <= 0) { //CHECK FOR ENEMY DEATH.
						entities.removeValue(e, false);
						players.get(0).addScore(e.getWorth());
						level.setEnemiesKilled(level.getEnemiesKilled()+1);
						
						if ((int) (Math.random() * 7) == 0) { //DROP ITEM.
							addEntity(itemFactory
									.buildRandom(e.getPosition().cpy().add(0, 80)));
						}
					}
					if (m.getPower() <= 0) { //REMOVE MISSILE IF IT'S POWER IS EMPTY.
						entities.removeValue(m, false);
					}

					GameScreen.pm.addEffect(m.getPosition().x+m.getTexture().getWidth(), m.getMidPoint().y);
					SoundManager.play(Assets.manager.get(Assets.HIT, Sound.class));
					break;
				}
			}
			if (e.getHealth() <= 0) { //ENEMY GOT AWAY.
				entities.removeValue(e, false);
			}

			for(Player p : players) {
				if (e.getBounds().overlaps(p.getBounds())) { //PLAYER CRASHES INTO ENEMY.
					loseLife(p, e);				
				}
			}			
		}
			
		for (Missile m : getMissiles()) {
			for (Player p : players) {
				if (p.getBounds().overlaps(m.getBounds()) && m.isEnemies()) {
					loseLife(p, m);
				}
			}
			
			if (m.checkEnd()) {
				entities.removeValue(m, false);
			}
		}		

		for (Item i : getItems()) {
			for (Player p : players) {

				if (i.getBounds().overlaps(p.getBounds())) { // COLLECT ITEM.
					GUIManager.addItemMessage(i.getName(), i.getPosition(),
							Fonts.GUIFont);
					i.activate(p);
					SoundManager.play(Assets.manager.get(Assets.ITEM_COLLECT, Sound.class));
					entities.removeValue(i, false);
				} else if (i.pos.x < -200 || i.pos.y < -200) { // ITEM LOST.
					entities.removeValue(i, false);
				}
			}
		}
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public Array<Enemy> getEnemies() {
		Array<Enemy> ret = new Array<Enemy>();
		for (Entity e : entities)
			if (e instanceof Enemy)
				ret.add((Enemy) e);
		return ret;
	}
	
	private Array<Missile> getMissiles() {
		Array<Missile> ret = new Array<Missile>();
		for (Entity e : entities)
			if (e instanceof Missile)
				ret.add((Missile) e);
		return ret;
	}

	
	public Array<Item> getItems() {
		Array<Item> ret = new Array<Item>();
		for (Entity e : entities)
			if (e instanceof Item)
				ret.add((Item) e);
		return ret;
	}

	public Array<Player> getPlayers() {
		return players;
	}
	
	private void loseLife(Player player, Entity entity) {
		if (!player.isInvinsible()) {
			player.loseLife();		
			SoundManager.play(Assets.manager.get(Assets.HIT, Sound.class));
			
			if(entity instanceof Missile) {
				entities.removeValue(entity, false);
			}		
		}
	}

	public void setPaused(boolean paused) {		
		for (Player p : players) {
			p.setInputProcessor(paused);
			p.getGun().setChargingShot(false);
		}
	}

	public void recalibratePlayer() {
		players.get(0).recalibrate();		
	}

	public Level getLevel() {
		return level;
	}
}

package com.yellowbyte.jjss.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.gameobjects.Entity;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.gameobjects.MissileFactory;
import com.yellowbyte.jjss.gameobjects.MissileFactory.MissileType;
import com.yellowbyte.jjss.media.Assets;

public class Enemy extends Entity {

	private int width = 155;
	private int height = 181;
	
	protected EntityManager em;
	private int worth = 100;
	private int health = 1;
	protected boolean canShoot = false;
	private Animation animation;
	protected float stateTime = 0f;
	protected MissileFactory missileFactory;
	
	public Enemy(Texture texture, int health, int worth, boolean canShoot, Vector2 pos, Vector2 direction, EntityManager em) {
		super(texture, pos, direction);
		
		this.health = health;
		this.em = em;
		
		this.worth = worth;
		this.canShoot = canShoot;
		
		AnimationManager am = new AnimationManager();
		//this.animation = am.build(texture);
		
		missileFactory = new MissileFactory();		
	}

	@Override
	public void update() {		
		
		
		pos.add(direction);
		
		//direction.y = -(float)Math.cos(pos.x / 200); // Sine wave MOTHAFUCKA!!!!!!!
		if (pos.x <= -texture.getWidth()-400 || pos.y < -400 || pos.y > JellyGame.HEIGHT+400 || pos.x > JellyGame.WIDTH+400) {
			this.setHealth(0);
		}
		
		
		if (canShoot) {
			if ((int) (Math.random() * 200) == 28) {

				em.addEntity(missileFactory.getMissile(
						MissileType.enemybasic,
						pos.cpy().add(
								texture.getWidth()
										/ 2
										- Assets.manager.get(Assets.MISSILE,
												Texture.class).getWidth() / 2,
								texture.getHeight() / 2 - 10), em));
			}
		}
		stateTime += Gdx.graphics.getDeltaTime();
	}
	
	@Override
	public void render(SpriteBatch sb) {	
		sb.draw(texture, pos.x, pos.y);
		//sb.draw(animation.getKeyFrame(stateTime, true), pos.x, pos.y);
	}
	
	private class AnimationManager {
		public Animation build(Texture texture) {
			TextureRegion P1 = new TextureRegion(texture, 0, 0, width, height);
			TextureRegion P2 = new TextureRegion(texture, width, 0, width, height);
			TextureRegion P3 = new TextureRegion(texture, width*2, 0, width, height);
			Animation ANIM = new Animation(1/10f, P1, P2, P3, P2);
			ANIM.setPlayMode(Animation.PlayMode.LOOP);
			
			return ANIM;
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(pos.x, pos.y, width, texture.getHeight());
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int i) {
		health = i;
		
	}

	public int getWorth() {
		return worth;
	}
	
}
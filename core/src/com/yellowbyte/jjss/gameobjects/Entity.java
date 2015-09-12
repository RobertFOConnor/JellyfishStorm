package com.yellowbyte.jjss.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	
	protected Texture texture;
	protected Vector2 pos, direction, midPoint;
	
	
	
	public Entity(Texture texture, Vector2 pos, Vector2 direction) {
		this.texture = texture;
		this.pos = pos;
		this.direction = direction;
	}

	public abstract void update();
	
	public void render(SpriteBatch sb) {
		sb.draw(texture, pos.x, pos.y);
	}
	
	public Vector2 getPosition() {
		return pos;
	}
	
	public Vector2 getMidPoint() {
		return new Vector2(pos.x+(texture.getWidth()/2), pos.y+(texture.getHeight()/2));
	}
	
	public Rectangle getBounds() {
		return new Rectangle(pos.x, pos.y, texture.getWidth(), texture.getHeight());
	}
	
	public boolean checkTouch(Vector2 touch) {
		if(getBounds().contains(touch)) {
			return true;
		}
		return false;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture t) {
		texture = t;
	}
	
	public void setDirection(float x, float y) {
		direction.set(x, y);
		direction.scl(Gdx.graphics.getDeltaTime());
	}
}

package com.yellowbyte.jjss.buttons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;

public class TextButton {

	private String message;
	
	private BitmapFont font;
	private float width, height;
	private Vector2 pos;
	
	public TextButton(String message, BitmapFont font, Vector2 pos) {
		this.message = message;
		this.font = font;
		this.pos = pos;
		width = font.getBounds(message).width;
		height = font.getBounds(message).height;
	}
	
	public void center() {
		pos.x += ((JellyGame.WIDTH/2)-width/2);
	}
	
	
	public void render(SpriteBatch sb) {
		font.draw(sb, message, pos.x, pos.y+(height/2));		
	}
	
	public boolean checkTouch(Vector2 touch) {
		if(getBounds().contains(touch)) {
			return true;
		}
		return false;
	}
	
	
	public Rectangle getBounds() {
		return new Rectangle(pos.x, pos.y-height/2, width, height);
	}
	
	public Vector2 getPosition() {
		return pos;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

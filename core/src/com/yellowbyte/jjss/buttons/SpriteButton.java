package com.yellowbyte.jjss.buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SpriteButton extends Sprite {

	public SpriteButton(TextureRegion region, Vector2 pos) {	
		super(region);
		setPosition(pos.x, pos.y);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getRegionWidth(), getRegionHeight());
	}
	
	public boolean checkTouch(Vector2 touch) {
		if(getBounds().contains(touch)) {
			return true;
		}
		return false;
	}
}

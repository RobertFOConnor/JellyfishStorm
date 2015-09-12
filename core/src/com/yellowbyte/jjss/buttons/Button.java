package com.yellowbyte.jjss.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.gameobjects.Entity;

public class Button extends Entity{

	public Button(Texture texture, Vector2 pos) {
		super(texture, pos, new Vector2());
	}

	@Override
	public void update() {
		
	}

	public boolean checkTouch(Vector2 touch) {
		if(getBounds().contains(touch)) {
			return true;
		}
		return false;
	}
}

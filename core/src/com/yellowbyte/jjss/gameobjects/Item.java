package com.yellowbyte.jjss.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.player.Player;

public abstract class Item extends Entity {
	
	private String name;
	
	public Item(String itemName, Texture texture, Vector2 pos, Vector2 direction) {
		super(texture, pos, direction);
		
		name = itemName;
	}

	@Override
	public void update() {
		pos.add(direction);		
	}

	public abstract void activate(Player player);

	
	public String getName() {
		return name;
	}
}

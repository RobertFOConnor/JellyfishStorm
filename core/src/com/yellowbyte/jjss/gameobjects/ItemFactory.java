package com.yellowbyte.jjss.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.player.Player;

public class ItemFactory {

	public enum ItemType {
		points,
		gun,
		speed,
		shield;
	}
	
	private static Vector2 itemDirection = new Vector2(-8, -2);
	
	public Item buildRandom(Vector2 pos) {

		Item item;
		int rand = (int) (Math.random() * 10);

		if(rand < 4) {
				item = new Item("WEAPONS UPGRADE!", Assets.manager.get(Assets.ITEM_GUN, Texture.class), pos, itemDirection) {

					@Override
					public void activate(Player player) {
						player.getGun().upgradeGuns(); 
					}
				};
		} else if(rand < 7) {
			item = new Item("SPEED BOOST!", Assets.manager.get(Assets.ITEM_SPEED, Texture.class), pos, itemDirection) {

				@Override
				public void activate(Player player) {
					player.addSpeed();
				}
			};	
		} else {
			
			item = new Item("+500 POINTS!", Assets.manager.get(Assets.ITEM, Texture.class), pos, itemDirection) {

				@Override
				public void activate(Player player) {
					player.addScore(500);
				}
			};
		}
	

		return item;
	}
}

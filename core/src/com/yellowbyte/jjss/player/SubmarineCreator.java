package com.yellowbyte.jjss.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.yellowbyte.jjss.gameobjects.EntityManager;

public class SubmarineCreator {
	
	public static Player create(int playerNum, PlayerPrototype p, EntityManager em) {
		return new Player(playerNum, p.image, p.health, p.speed, p.power, em);
	}
	
	
	public static PlayerPrototype createPrototype(String sub) {
		Json json = new Json();
		PlayerPrototype p = json.fromJson(PlayerPrototype.class,
				Gdx.files.internal("submarines/"+sub+".json"));
		return p;
	}
}

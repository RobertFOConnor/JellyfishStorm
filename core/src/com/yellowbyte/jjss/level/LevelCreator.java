package com.yellowbyte.jjss.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.yellowbyte.jjss.gameobjects.EntityManager;

public class LevelCreator {

	public static Level build(EntityManager em, int id) {
		Json json = new Json();
		LevelFactory l = json.fromJson(LevelFactory.class,
				Gdx.files.internal("levels/level"+id+".json"));
		return l.createLevel(em);
	}
}

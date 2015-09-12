package com.yellowbyte.jjss.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.media.Assets;

public class MissileFactory {

	public static int speed = 16;
	
	public enum MissileType {
		basic,
		duo,
		back,
		topball,
		botball,
		charged,
		enemybasic,
		pill,
		pillback,
		huge;
	}
	
	public Missile getMissile(MissileType type, Vector2 pos, EntityManager em) {

		if (type == null) {
			return null;
		}

		if (type == MissileType.basic) {
			return new Missile(pos, 1, new Vector2(speed,0), Assets.MISSILE, false, em);
		}else if (type == MissileType.back) {
			return new Missile(pos, 1, new Vector2(-speed,0), Assets.MISSILE_REV, false, em);
		} else if (type == MissileType.topball) {
			return new Missile(pos, 1, new Vector2(speed,10), Assets.MISSILE_BALL, false, em);
		} else if (type == MissileType.botball) {
			return new Missile(pos, 1, new Vector2(speed,-10), Assets.MISSILE_BALL, false, em);
		} else if (type == MissileType.charged) {
			return new Missile(pos, 8, new Vector2(22,0), Assets.MISSILE_BIG, false, em);
		} else if (type == MissileType.huge) {
			return new Missile(pos, 10, new Vector2(50,0), Assets.MISSILE_HUGE, false, em);
		} else if (type == MissileType.pill) {
			return new Missile(pos, 2, new Vector2(speed,0), Assets.MISSILE_PILL, false, em);
		} else if (type == MissileType.pillback) {
			return new Missile(pos, 2, new Vector2(-speed,0), Assets.MISSILE_PILL, false, em);
			
		}  else if (type == MissileType.enemybasic) {
			return new Missile(pos, 1, new Vector2(-14,0), Assets.MISSILE_BALL, true, em);
		}
		return null;
	}
}

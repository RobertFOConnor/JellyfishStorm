package com.yellowbyte.jjss.player.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.player.Player;
import com.yellowbyte.jjss.player.PlayerGun;

public class XBox360Controller extends PlayerController {		
	
	private Controller controller;
	private float analogX;
	private float analogY;
	private long SPEED;
	
	public XBox360Controller(Player player) {
		this.player = player;
		controller = JellyGame.getController();
	}
	
	
	@Override
	public void movePlayer() {

		SPEED = player.getSpeed();
		analogX = controller.getAxis(XBox360Pad.AXIS_LEFT_X);
		analogY = controller.getAxis(XBox360Pad.AXIS_LEFT_Y);

		if (analogY < -0.2f) { // up
			moveVertical(SPEED * -analogY, true);

		} else if (analogY > 0.2f) { // down
			moveVertical(SPEED * -analogY, false);
		} else {
			
			stopShip();
		}
		
		if (analogX < -0.2f || analogX > 0.2f) { //left|right
			moveHorizontal(SPEED*analogX);						
		}
		
		PlayerGun gun = player.getGun();
		if (controller.getAxis(XBox360Pad.AXIS_RIGHT_TRIGGER) < -0.5f) {
			if (!gun.isCharging()) {
				gun.setChargingShot(true);
			}
		} else {
			if (gun.isCharging()) {
				gun.releaseShot(player.getTexture(), player.getPosition());
			}
		}
	}
}
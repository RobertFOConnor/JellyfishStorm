package com.yellowbyte.jjss.player.controllers;

import com.badlogic.gdx.Gdx;
import com.yellowbyte.jjss.player.Player;

public class AccelerometerController extends PlayerController {
	private float accelX, accelY;
	private float midTilt = 0f;
	private static final int maxSensitivity = 7;
	private final int sensitivity = 3;
	
	
	public AccelerometerController(Player player) {
		this.player = player;
		midTilt = Gdx.input.getAccelerometerX(); //Takes the users positioning & uses that as the mid for ship.
	}
	
	@Override
	public void movePlayer() {
		
		accelX = Gdx.input.getAccelerometerX();
		accelY = Gdx.input.getAccelerometerY();
		int dir = 0;
		
		if (accelX < midTilt) { // UP
			dir = 1;
		} else if (accelX > midTilt) { // DOWN
			dir = 2;
		}
		
		if (dir == 1) { // up
			player.setDirection(getAccelSpeedX(), getAccelSpeedY());
			setMovingUp();

		} else if (dir == 2) { // down
			player.setDirection(getAccelSpeedX(), getAccelSpeedY());
			setMovingDown();

		} else {
			float XSpeed = getAccelSpeedX();
			player.setDirection(XSpeed, 0);
			player.setIdle();
		}
	}
	
	private float getAccelSpeedX() {
		long SPEED = player.getSpeed();
		float XSpeed = (SPEED*accelY)/(maxSensitivity-sensitivity);
		
		if(XSpeed > SPEED) {
			return SPEED;
		} else if(XSpeed < -SPEED) {
			return -SPEED;
		}
		return XSpeed;
	}
	
	private float getAccelSpeedY() {
		long SPEED = player.getSpeed();
		float YSpeed = (SPEED*((midTilt)-accelX))/(maxSensitivity-sensitivity);
		
		if(YSpeed > SPEED) {
			return SPEED;
		} else if(YSpeed < -SPEED) {
			return -SPEED;
		}
		return YSpeed;
	}
}
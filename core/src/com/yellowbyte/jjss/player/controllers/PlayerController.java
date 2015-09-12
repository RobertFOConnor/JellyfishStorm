package com.yellowbyte.jjss.player.controllers;

import com.yellowbyte.jjss.player.Player;

public abstract class PlayerController {
	
	protected Player player;
	private float xSpeed = 0;
	private float ySpeed = 0;
	private float slowdownSpeed = 18;
	
	
	public abstract void movePlayer();
	
	
	protected void stopShip() {
		xSpeed = deccelerateShip(xSpeed);
		ySpeed = deccelerateShip(ySpeed);
		
		player.setDirection(xSpeed, ySpeed);
		player.setIdle();
	}
	
	private float deccelerateShip(float moveDirection) {
		if(moveDirection != 0) {
			if(moveDirection > 0) {
				moveDirection -= slowdownSpeed;
			} else if(moveDirection < 0) {
				moveDirection += slowdownSpeed;
			}
			
			if(Math.abs(moveDirection) < 5) {
				moveDirection = 0;
			}
		}
		return moveDirection;
	}

	protected void moveHorizontal(float hSpeed) {
		xSpeed = hSpeed;
		long SPEED = player.getSpeed();
		
		if(player.isMovingUp()) {
			player.setDirection(hSpeed, SPEED);	
		} else if(player.isMovingDown()) {
			player.setDirection(hSpeed, -SPEED);
		} else {
			player.setDirection(hSpeed, 0);	
		}			
	}
	
	protected void moveVertical(float vSpeed, boolean up) {
		ySpeed = vSpeed;
		player.setDirection(0, vSpeed);
		
		if(up) {
			setMovingUp();
		} else {
			setMovingDown();
		}
	}
	
	protected void setMovingUp() {
		player.setMovingUp(true);
	}
	
	protected void setMovingDown() {
		player.setMovingDown(true);
	}
}



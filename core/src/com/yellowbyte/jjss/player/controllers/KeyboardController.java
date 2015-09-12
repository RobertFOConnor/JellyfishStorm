package com.yellowbyte.jjss.player.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.yellowbyte.jjss.player.Player;

public class KeyboardController extends PlayerController {

	public KeyboardController(Player player) {
		this.player = player;
	}

	@Override
	public void movePlayer() {
		if (Gdx.input.isKeyPressed(Keys.UP)) { // up
			moveVertical(player.getSpeed(), true);

		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) { // down
			moveVertical(-player.getSpeed(), false);

		} else {
			stopShip();
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			moveHorizontal(-player.getSpeed());

		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			moveHorizontal(player.getSpeed());
		}
	}
}

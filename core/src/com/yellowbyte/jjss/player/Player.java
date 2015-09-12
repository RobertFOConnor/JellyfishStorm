package com.yellowbyte.jjss.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.gameobjects.Entity;
import com.yellowbyte.jjss.gameobjects.EntityManager;
import com.yellowbyte.jjss.gameobjects.MissileFactory;
import com.yellowbyte.jjss.media.Assets;
import com.yellowbyte.jjss.player.controllers.AccelerometerController;
import com.yellowbyte.jjss.player.controllers.KeyboardController;
import com.yellowbyte.jjss.player.controllers.PlayerController;
import com.yellowbyte.jjss.player.controllers.XBox360Controller;
import com.yellowbyte.jjss.screens.GameOverScreen;
import com.yellowbyte.jjss.screens.GameScreen;
import com.yellowbyte.jjss.screens.ScreenManager;

public class Player extends Entity implements InputProcessor {

	private PlayerGun gun;
	private PlayerController pController;
	
	private long START_SPEED = 500; //The players starting speed.
	private final long MAX_SPEED = 900; //The players maximum speed.
	private long SPEED = START_SPEED; //The players current speed.
	
	
	private float boundry = 40;	
	float xLimit = JellyGame.WIDTH-texture.getWidth()-boundry;
	float yLimit = JellyGame.HEIGHT-texture.getHeight()-boundry;
	
	private int score = 0;
	private int maxLives = 5;
	private int lives = 5;
	
	private final Texture PLAYER;
	private final Texture PLAYER_UP;
	private final Texture PLAYER_DOWN;
	
	private boolean movingUp, movingDown = false;
	
	//RESPAWN VARS
	private long deathTime = 0;
	private final long respawnCooldown = 3000;
	private boolean invinsible = false;
	
	//ACCELEROMETER VARS
	private boolean usesAccelerometer = false;
	private float accelX, accelY;
	private float midTilt = 0f;
	private static final int maxSensitivity = 7;
	private static int sensitivity = 3;
	
	
	public Player(int playerNum, String ship, int health, int speed, int power, EntityManager entityManager) {
		super(Assets.manager.get(ship+".png", Texture.class), new Vector2(200, JellyGame.HEIGHT/2), new Vector2(0,0));
		
		maxLives= health;
		lives = maxLives;
		
		START_SPEED = speed*100;
		SPEED = START_SPEED;
		MissileFactory.speed = power;
		
		
		PLAYER = Assets.manager.get(ship+".png", Texture.class);
		PLAYER_UP = Assets.manager.get(ship+"_up.png", Texture.class);
		PLAYER_DOWN = Assets.manager.get(ship+"_down.png", Texture.class);
		
		gun = new PlayerGun(entityManager);

		usesAccelerometer = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		midTilt = Gdx.input.getAccelerometerX(); //Takes the users positioning & uses that as the mid for ship.
		Gdx.input.setInputProcessor(this);
		
		if (JellyGame.hasControllers && playerNum == 1) {
			pController = new XBox360Controller(this); //Use XBox 360 controller.
			
		} else if(usesAccelerometer) {
			pController = new AccelerometerController(this); //Use Android accelerometer.
			
		} else {
			pController = new KeyboardController(this); //Use PC keyboard.
		}
	}

	@Override
	public void update() {		
		pos.add(direction);			
		checkBoundries();		
		
		pController.movePlayer();		

		if (invinsible) {
			if ((System.nanoTime() - deathTime) / 1000000 >= respawnCooldown) {
				texture = PLAYER;
				invinsible = false;
			}
		}
	}

	

	private void checkBoundries() {		
		if(pos.y < boundry+80) {
			pos.y = boundry+80;
		} else if(pos.y > yLimit) {
			pos.y = yLimit;
		} 
		
		if(pos.x < boundry) {
			pos.x = boundry;
		} else if(pos.x > xLimit) {
			pos.x = xLimit;
		}	
	}


	
	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
		
		if (movingUp) {
			setTexture(PLAYER_UP);
		}
	}
	
	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
		
		if (movingDown) {
			setTexture(PLAYER_DOWN);
		}
	}	
	
	public boolean isMovingUp() {
		return movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	@Override
	public void render(SpriteBatch sb) {
		if(invinsible) {
			sb.setColor(Color.LIGHT_GRAY);
		}
		
		sb.draw(texture, pos.x, pos.y);
		sb.setColor(Color.WHITE);
	}
	
	public float getAccelX() {
		return accelX;
	}	

	public boolean isMovingLeft() {
		return movingUp;
	}

	public boolean isMovingRight() {
		return movingDown;
	}
	
	public int getMaxLives() {
		return maxLives;
	}

	public int getLives() {
		return lives;
	}
	
	public int getScore() {
		return score;
	}

	public void addSpeed() {
		if(SPEED < MAX_SPEED) {
			SPEED += 100;
		}		
	}
	
	public void addScore(int worth) {
		score += worth;		
	}
	
	
	public boolean isInvinsible() {
		return invinsible;
	}
	

	public PlayerGun getGun() {
		return gun;
	}

	
	public void loseLife() {
		GameScreen.pm.addEffect(pos.x, pos.y);
		lives--;

		if (lives <= 0) {
			ScreenManager.setScreen(new GameOverScreen(score, false));
		} else {
			startRespawn();
		}
		
		gun.resetCharge();
	}

	private void startRespawn() {
		SPEED = START_SPEED;
		
		gun.loseUpgrade();
		
		pos.set(new Vector2(texture.getWidth(), JellyGame.HEIGHT/2));
		//texture = Assets.manager.get(Assets.PLAYER_INV, Texture.class);
		invinsible = true;
		deathTime = System.nanoTime();
	}
	
	
	@Override
    public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE) {
			gun.setChargingShot(true);
		}
        return true;
    }


	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.SPACE) {
			gun.releaseShot(texture, pos);
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		gun.setChargingShot(true);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		gun.releaseShot(texture, pos);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void setInputProcessor(boolean paused) {
		if(paused) {
			Gdx.input.setInputProcessor(null);
		} else {
			Gdx.input.setInputProcessor(this);
		}
	}
	
	public void recalibrate() {
		midTilt = Gdx.input.getAccelerometerX(); //Takes the users positioning & uses that as the mid for ship.
	}

	public int getSensitivity() {
		return sensitivity;
	}

	public void addSensitivity() {
		sensitivity++;
		if(sensitivity > 6) {
			sensitivity = 1;
		}		
	}
	
	public long getSpeed() {
		return SPEED;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexutre(Texture pLAYER) {
		setTexture(pLAYER);
		
	}

	public void setIdle() {
		setMovingUp(false);
		setMovingDown(false);
		setTexutre(PLAYER);
	}
}
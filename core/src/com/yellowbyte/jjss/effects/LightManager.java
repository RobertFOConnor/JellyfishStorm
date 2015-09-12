package com.yellowbyte.jjss.effects;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;
import com.yellowbyte.jjss.player.Player;

public class LightManager {

	private final Array<Light> lights = new Array<Light>();
	
	private OrthoCamera camera;
	private World world;
	private Box2DDebugRenderer renderer;
	private FPSLogger logger;
	private ConeLight l, r;
	//private PointLight playerLight;
	
	private RayHandler handler;
	
	float brightness = 0.9f;
	float currAngle = 90;
	float targetAngle = 90;
	
	float currDegree = 10;
	

	public LightManager(OrthoCamera camera) {
		
		this.camera = camera;
		
		world = new World(new Vector2(0, -9.8f), false);
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();
		
		handler = new RayHandler(world);
		handler.setCombinedMatrix(camera.combined);
		handler.setAmbientLight(brightness);
		
		
		//playerLight = new PointLight(handler, 10, Color.BLACK, 200, JellyGame.WIDTH/2, 800);
		l = new ConeLight(handler, 10, Color.WHITE, 1500, 0, JellyGame.HEIGHT-200, 90, 10);
		r = new ConeLight(handler, 10, Color.WHITE, 1500, 0, JellyGame.HEIGHT-200, 90, 10);
		
	}

	public void update() {
		
		for(Light pl : lights) {	
			//update lights
		}		
		
		//DARKEN BG
		//brightness -= 0.0001f;
		handler.setAmbientLight(brightness);
		
		handler.update();
	}
	
	public void render() {
		renderer.render(world, camera.combined);
		handler.render();
		
		world.step(1/60f, 6, 2);
		logger.log();
	}
	
	
	public void addLight(Light l) {
		lights.add(l);
	}
	
	public void updateHeadlights(Player p) {
		/*Vector2 pos = new Vector2(p.getPosition().x+p.texture.getWidth()/2, p.getPosition().y+p.texture.getHeight());
		
		l.setPosition(pos.x-25, pos.y);
		r.setPosition(pos.x+25, pos.y);
		*/
		//playerLight.setPosition(p.getMidPoint());
		
		if(p.isMovingLeft()) {
			targetAngle = 100;
		} else if(p.isMovingRight()) {
			targetAngle = 80;
		} else {
			targetAngle = 90;
		}
		
		if(currAngle < targetAngle) {
			currAngle++;
		} else if(currAngle > targetAngle) { 
			currAngle--;
		}
		
		l.setDirection(currAngle);
		r.setDirection(currAngle);
	}
}

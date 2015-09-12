package com.yellowbyte.jjss.effects;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.yellowbyte.jjss.JellyGame;
import com.yellowbyte.jjss.camera.OrthoCamera;

public class TorchManager {

private final Array<Light> lights = new Array<Light>();
	
	private OrthoCamera camera;
	private World world;
	private Box2DDebugRenderer renderer;
	private FPSLogger logger;
	private ConeLight torch;
	private float direction = 0;
	
	private RayHandler handler;
	
	float brightness = 0.6f;
	float currAngle = 90;
	float targetAngle = 90;
	
	float currDegree = 10;
	

	public TorchManager(OrthoCamera camera) {
		
		this.camera = camera;
		
		world = new World(new Vector2(0, -9.8f), false);
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();
		
		handler = new RayHandler(world);
		handler.setCombinedMatrix(camera.combined);
		
		
		torch = new ConeLight(handler, 1000, Color.WHITE, 800, JellyGame.WIDTH/2, JellyGame.HEIGHT/2, 0, 20);		
	}

	public void update() {
		
		for(Light pl : lights) {	
			//update lights
		}		
		
		
		torch.setDirection(direction);
		
		//DARKEN BG
		brightness -= 0.0001f;
		handler.setAmbientLight(brightness);
		
		handler.update();
	}
	
	public void render() {
		renderer.render(world, camera.combined);
		handler.render();
		
		world.step(1/60f, 6, 2);
		logger.log();
	}
	
	public float getDirection() {
		return direction;
	}
	
	public void setDirection(float direction) {
		this.direction = direction;
	}
	
	
	public void addLight(Light l) {
		lights.add(l);
	}
}

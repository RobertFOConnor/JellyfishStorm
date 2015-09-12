package com.yellowbyte.jjss.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen {	
	
	public abstract void create();
	
	public abstract void update();
	
	public abstract void render(SpriteBatch sb);
	
	public abstract void resize(int w, int h);
	
	public abstract void dispose();
	
	public abstract void show();
	
	public abstract void hide();
	
	public abstract void pause();
	
	public abstract void resume();

	public abstract void goBack();
}

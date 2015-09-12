package com.yellowbyte.jjss.gui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.yellowbyte.jjss.media.Fonts;
import com.yellowbyte.jjss.tween.SpriteAccessor;
import com.yellowbyte.jjss.tween.SpriteText;

public class GUIMessage extends SpriteText {

	private boolean finished = false;

	private TweenManager tweenManager;
	private long startTime, delta;

	public GUIMessage(String message, Vector2 targetPos, BitmapFont font) {
		super(message, font);
		setPosition(targetPos.x, -200);
		
		if(font.equals(Fonts.GUIFont)) {
			setPosition(targetPos.x, targetPos.y-100);
		}

		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		tweenManager = new TweenManager();
		
		
		TweenCallback myCallBack = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				finished = true;
			}
		};

		Tween.to(this, SpriteAccessor.POS_XY, 100f).target(getX(), targetPos.y)
		.ease(TweenEquations.easeOutBack).setCallback(myCallBack)
		.setCallbackTriggers(TweenCallback.END).start(tweenManager);
		
		startTime = TimeUtils.millis();
	}

	public void update() {
		delta = (TimeUtils.millis() - startTime + 1000) / 1000;
		tweenManager.update(delta);
	}

	public boolean isFinished() {
		return finished;
	}
}

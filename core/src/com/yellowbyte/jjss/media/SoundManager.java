package com.yellowbyte.jjss.media;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.yellowbyte.jjss.JellyGame;

public class SoundManager {

	public static boolean soundFXEnabled = true;
	public static boolean musicEnabled = false;
	
	public static void play(Sound s) {
		if(soundFXEnabled) {
			s.play();
		}		
	}

	public static void loop(Sound s) {
		if(soundFXEnabled) {
			s.loop();
		}
	}
	
	public static void switchMusic(String music) {
		if (musicEnabled) {
			if(JellyGame.GAME_MUSIC.isPlaying()) {
				JellyGame.GAME_MUSIC.stop();
			}
			JellyGame.GAME_MUSIC = Assets.manager.get(music, Music.class);
			JellyGame.GAME_MUSIC.setLooping(true);
			JellyGame.GAME_MUSIC.play();
		}
	}
}

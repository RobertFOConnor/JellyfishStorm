package com.yellowbyte.jjss.media;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {

	public static BitmapFont GUIFont, SubFont, TitleFont, MenuFont, LevelFont;
	
	public static void loadFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 45;
        GUIFont = generator.generateFont(parameter);
        
        parameter.size = 55;
        SubFont = generator.generateFont(parameter);
        
        parameter.size = 95;
        MenuFont = generator.generateFont(parameter);
        
        parameter.size = 120;
        LevelFont = generator.generateFont(parameter);
        
        parameter.size = 150;
        TitleFont = generator.generateFont(parameter);
	}
	
	public static float getWidth(BitmapFont f, String s) {
		return f.getBounds(s).width;
	}
}

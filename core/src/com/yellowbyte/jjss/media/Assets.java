package com.yellowbyte.jjss.media;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	
    public static AssetManager manager = new AssetManager();
    
    public static final String PLAYER = "player.png";	
    public static final String PLAYER_UP = "player_up.png";	
    public static final String PLAYER_DOWN = "player_down.png";	
    
    public static final String PINKIE = "pinkie.png";	
    public static final String PINKIE_UP = "pinkie_up.png";	
    public static final String PINKIE_DOWN = "pinkie_down.png";	
    
    public static final String PLAYER_GUI = "player_gui.png";	
    
    //MISSILES
    public static final String MISSILE = "missiles/missile.png";
    public static final String MISSILE_REV = "missiles/missile_rev.png";
    public static final String MISSILE_BIG = "missiles/missile_big.png";
    public static final String MISSILE_HUGE = "missiles/missile_huge.png";
    public static final String MISSILE_BALL = "missiles/missile_ball.png";
    public static final String MISSILE_PILL = "missiles/missile_pill.png";
    
    
    public static final String BG = "bg.png";
    public static final String BG_FAR = "far_bg.png";
    public static final String BG_SHINE = "bg_shine.png";
    public static final String BG_ROCK = "bg_rock.png";
    public static final String BG_ROCK2 = "bg_rock2.png";
    public static final String ITEM = "item.png";
    public static final String ITEM_SPEED = "item_speed.png";
    public static final String ITEM_GUN = "item_gun.png";
    public static final String GUN_UPGRADE = "gun_upgrade.png";
    public static final String LEVEL_BUTTON = "button_level.png";
    
    //MENUSCREEN
    public static final String XBOX_MENU = "xbox_menubuttons.png";
    public static final String MENU_BG= "menu_bg.png";
    public static final String BLUEPRINT_BG= "blueprint.png";
    public static final String PLAYGAME= "button_playgame.png";
    public static final String SETTINGS= "button_settings.png";
    public static final String HELP= "button_help.png";
    public static final String SUB_SELECT = "sub_select.png";
    
    //ENEMIES
    public static final String SHARK = "enemies/shark.png";
    public static final String JELLY = "enemies/jelly.png";
    public static final String WHALE = "enemies/whale.png";
    public static final String BOSS_EEL = "enemies/boss_eel.png";
    
    
    public static final String ALPHA = "alpha.png";
    public static final String PAUSE = "paused.png";

    
    //Sounds
    public static final String MAIN_THEME = "sounds/336722_Cloudy_underwater.mp3";
    public static final String BOSS_THEME = "sounds/76388_eclips.mp3";
    public static final String MISSILE_SOUND = "sounds/missile.wav";
    public static final String MISSILE_BIG_SOUND = "sounds/missile_big.wav";
    public static final String HIT = "sounds/hit.wav";
    public static final String CHARGED = "sounds/charged.wav";
    public static final String CHARGING = "sounds/charging.wav";
    public static final String BEEP = "sounds/beep.wav";
    public static final String MENU_SELECT = "sounds/menu_select.wav";
    public static final String ITEM_COLLECT = "sounds/item_collect.wav";


    
    //Fonts
    //public static final String IMPACT = "impact.fnt";

    
    public static void load() {// Loads Assets
    	
    	//Menu Images
    	manager = new AssetManager();
        manager.load(PLAYER, Texture.class);
        manager.load(PLAYER_UP, Texture.class);
        manager.load(PLAYER_DOWN, Texture.class);
        
        manager.load(PINKIE, Texture.class);
        manager.load(PINKIE_UP, Texture.class);
        manager.load(PINKIE_DOWN, Texture.class);

        manager.load(PLAYER_GUI, Texture.class);
        
        manager.load(MISSILE, Texture.class);
        manager.load(MISSILE_REV, Texture.class);
        manager.load(MISSILE_BIG, Texture.class);
        manager.load(MISSILE_HUGE, Texture.class);
        manager.load(MISSILE_BALL, Texture.class);
        manager.load(MISSILE_PILL, Texture.class);
        manager.load(BG, Texture.class);
        manager.load(BG_FAR, Texture.class);
        manager.load(BG_SHINE, Texture.class);
        manager.load(BG_ROCK, Texture.class);
        manager.load(BG_ROCK2, Texture.class);
        manager.load(ITEM, Texture.class);
        manager.load(ITEM_SPEED, Texture.class);
        manager.load(ITEM_GUN, Texture.class);
        manager.load(GUN_UPGRADE, Texture.class);
        manager.load(LEVEL_BUTTON, Texture.class);
        manager.load(XBOX_MENU, Texture.class);
        manager.load(MENU_BG, Texture.class);
        manager.load(PLAYGAME, Texture.class);
        manager.load(SETTINGS, Texture.class);
        manager.load(HELP, Texture.class);
        manager.load(BLUEPRINT_BG, Texture.class);
        manager.load(SUB_SELECT, Texture.class);
        manager.load(ALPHA, Texture.class);
        manager.load(PAUSE, Texture.class);

        
        //ENEMIES
        manager.load(SHARK, Texture.class);
        manager.load(JELLY, Texture.class);
        manager.load(WHALE, Texture.class);
        manager.load(BOSS_EEL, Texture.class);
        
        //Sounds
        manager.load(MAIN_THEME, Music.class);
        manager.load(BOSS_THEME, Music.class);
        manager.load(MISSILE_SOUND, Sound.class);
        manager.load(MISSILE_BIG_SOUND, Sound.class);
        manager.load(HIT, Sound.class);
        manager.load(CHARGED, Sound.class);
        manager.load(CHARGING, Sound.class);
        manager.load(BEEP, Sound.class);
        manager.load(MENU_SELECT, Sound.class);
        manager.load(ITEM_COLLECT, Sound.class);

        //Fonts
        //manager.load(IMPACT, BitmapFont.class);
    }

    public static void dispose() {
    	manager.dispose();
    }

    public static boolean update() {
        return manager.update();
    }
}
package com.apps.crashpenguin.utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class Sounds {

	public static Sound sndApply;
	public static Sound sndBtn;
	public static Sound sndItem;
	public static Sound sndLose;
	public static Music sndMusic;
	public static Music sndCrashTime;
	public static Sound sndVozAsustado;
	public static Sound sndVozCae;
	public static float masterVol;
		
	public static void load(){
		
		sndApply = load("sounds/sndApply.ogg");
		sndBtn = load("sounds/sndBtn.ogg");
		sndItem = load("sounds/sndItem.ogg");
		sndLose = load("sounds/sndLose.ogg");
		sndMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/sndMusic.ogg"));
		sndCrashTime = Gdx.audio.newMusic(Gdx.files.internal("sounds/sndCrashTime.ogg"));
		sndVozAsustado = load("sounds/sndVozAsustado.ogg");
		sndVozCae = load("sounds/sndVozCae2.ogg");
		
		masterVol = 1f;
		sndLose.setVolume(0,masterVol/2);
		sndVozAsustado.setVolume(1, masterVol/1.47f);	
		sndItem.setVolume(2, masterVol);
		sndApply.setVolume(3, masterVol);
		sndBtn.setVolume(4, masterVol);
		sndVozCae.setVolume(5, masterVol);
		
				
	}
	
	private static com.badlogic.gdx.audio.Sound load (String name) {
		return Gdx.audio.newSound(Gdx.files.internal(name));
	}
	
	
	public static void setMasterVol(float value){
		masterVol = value;
	}

	public static  float getMasterVol(){
		return masterVol;
	}
	public static void dispose() {
		sndApply.dispose();
		sndBtn.dispose();
		sndItem.dispose();
		sndLose.dispose();
		sndMusic.dispose();
	
		sndVozAsustado.dispose();
		sndVozCae.dispose();
		sndCrashTime.dispose();
		
	}
	
	
}

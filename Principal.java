package com.apps.crashpenguin;

import com.apps.crashpenguin.pantallas.PantallaSplash;
import com.apps.crashpenguin.utilidades.Sounds;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Principal extends Game {
	
	public SpriteBatch batch; 	
	
	private AdsController adsController;
	 
	public Principal(AdsController adsController){
		if (adsController != null) {
            this.adsController = adsController;
        } else {
            this.adsController = new DummyAdsController();
        }
	   
	}
	
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
		
	
		
		setScreen(new PantallaSplash(this,  this.adsController));
		
		
	}
	
	@Override
	public void render() {
		super.render();
	}
 
	@Override
	public void dispose() {
		
		super.dispose();
		batch.dispose();
		System.gc();
		
	}
	
	@Override
	public void resume() {
		
		super.resume();
		
	}
	
}

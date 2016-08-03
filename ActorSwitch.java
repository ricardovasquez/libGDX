package com.apps.crashpenguin.actores;

import com.apps.crashpenguin.utilidades.ResourceManager;
import com.apps.crashpenguin.utilidades.Sounds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Disposable;

public class ActorSwitch extends Actor implements Disposable {

	private TextureAtlas atlasSwitch;
	private AtlasRegion switchState;
	private Boolean isOn = true;
	
	

	public ActorSwitch() {

		atlasSwitch = ResourceManager.manager.get(ResourceManager.ATLAS_ELEMENTOS);
		switchState = atlasSwitch.findRegion("switchOn");
		setSize(74, 50);
		
	

		addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (this.isOn()) {
					this.state(false);

				} else {
					this.state(true);
				}
				Sounds.sndBtn.play(Sounds.getMasterVol());
				return super.touchDown(event, x, y, pointer, button);
			}

			public void state(boolean on) {
				if (on) {

					
					switchOn();
					
					
				} else {
					
					switchOff();
				}

			}

			public boolean isOn() {
				return isOn;
			}

		});

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(switchState, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	public void switchOn(){
		switchState = atlasSwitch.findRegion("switchOn");
		isOn = true;
		
			Sounds.sndMusic.setVolume(1);
			
			Sounds.sndCrashTime.setVolume(1);
			ResourceManager.preferences.putInteger("Music", 1);
			Sounds.setMasterVol(1);
			ResourceManager.preferences.putInteger("Sound", 1);
			
		ResourceManager.preferences.flush();
	}
	
	public void switchOff(){
		switchState = atlasSwitch.findRegion("switchOff");
		isOn = false;

			Sounds.sndMusic.setVolume(0);
			
			Sounds.sndCrashTime.setVolume(0);
			ResourceManager.preferences.putInteger("Music", 0);

			Sounds.setMasterVol(0);
			ResourceManager.preferences.putInteger("Sound", 0);
			ResourceManager.preferences.flush();
	}

	@Override
	public void dispose() {
		atlasSwitch.dispose();
		ResourceManager.dispose();
		System.gc();

	}

}

package com.apps.crashpenguin.actores;

import com.apps.crashpenguin.utilidades.ResourceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ActorAnimado extends Actor implements Disposable {

	private TextureAtlas atlas;
	private Animation animacion;
	private boolean looping;
	private float frameRate = 10;

	private float elapsedTime = 0f;

	public ActorAnimado(String nombreAtlas, float width, float height) {

		atlas = ResourceManager.manager.get("atlas/" + nombreAtlas + ".atlas");
		animacion = new Animation(2 / 4f, atlas.getRegions());
		animacion.setPlayMode(PlayMode.LOOP_PINGPONG);
		looping = true;
		setSize(width, height);
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		elapsedTime += Gdx.graphics.getDeltaTime() * frameRate;
		batch.draw(animacion.getKeyFrame(elapsedTime, looping), getX(), getY(), getOriginX(), getOriginY(), getWidth(),
				getHeight(), getScaleX(), getScaleY(), getRotation());

	}

	public void setFrameRate(float vel) {
		frameRate = vel;
	}

	public void restart(){
		
		elapsedTime = 0;
				
	}

	public void playMode(PlayMode pm){
		animacion.setPlayMode(pm);
		looping = false;
	}
	@Override
	public void dispose() {
		this.dispose();
		ResourceManager.dispose();
		atlas.dispose();
		System.gc();

	}
}

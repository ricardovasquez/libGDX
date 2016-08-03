package com.apps.crashpenguin.actores;

import com.apps.crashpenguin.utilidades.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ActorNinePatch extends Actor implements Disposable {

	private TextureAtlas atlas;
	private NinePatch patch;

	public ActorNinePatch(String imagen, int padLeft, int padRight, int padTop, int padBottom) {

		atlas = ResourceManager.manager.get(ResourceManager.ATLAS_ELEMENTOS);
		patch = new NinePatch(atlas.findRegion(imagen), padLeft, padRight, padTop, padBottom);
		
		patch.setMiddleHeight(10);
		patch.setMiddleWidth(10);
		
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		patch.draw(batch, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	

	

	@Override
	public void dispose() {
		atlas.dispose();
		ResourceManager.dispose();
		System.gc();

	}

}

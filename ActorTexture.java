package com.apps.crashpenguin.actores;

import com.apps.crashpenguin.utilidades.ResourceManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ActorTexture extends Actor implements Disposable {
	private Texture texture;

	public ActorTexture(String name, float sizeX, float sizeY) {
		texture = ResourceManager.manager.get(name);

		setSize(sizeX, sizeY);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY());
	}

	@Override
	public void dispose() {
		texture.dispose();
		ResourceManager.dispose();
		System.gc();

	}

}
